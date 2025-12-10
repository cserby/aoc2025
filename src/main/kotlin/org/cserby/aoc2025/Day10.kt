package org.cserby.aoc2025

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.last

object Day10 {
    data class Machine(
        val stateWidth: Int,
        val expectedLights: Int,
        val buttons: List<Int>,
    ) {
        tailrec fun leastButtonPresses(
            toCheckButtonIndexSequences: List<Pair<List<Int>, Int>> = (0 until buttons.size).map { listOf(it) to 0 },
            visitedStates: List<Int> = listOf(0),
        ): List<Int> {
            val (sequence, prevState) = toCheckButtonIndexSequences.first()
            val newState = prevState xor buttons[sequence.last()]
            return when (newState) {
                expectedLights -> {
                    return sequence
                }

                in visitedStates -> {
                    leastButtonPresses(toCheckButtonIndexSequences.drop(1), visitedStates)
                }

                else -> {
                    leastButtonPresses(
                        toCheckButtonIndexSequences =
                            toCheckButtonIndexSequences.drop(1) + (
                                (0 until buttons.size).map { sequence + it to newState }
                            ),
                        visitedStates = visitedStates + listOf(newState),
                    )
                }
            }
        }
    }

    fun parseExpectedLights(lights: String): Int =
        lights.filterNot { it in "[]" }.foldIndexed(0) { index, acc, ch ->
            when (ch) {
                '#' -> (acc or (1 shl index))
                '.' -> acc
                else -> throw IllegalStateException("$ch is not a valid light state")
            }
        }

    fun parseButtons(buttons: List<String>): List<Int> =
        buttons.map { button ->
            button.filterNot { it in "()" }.split(",").map { it.toInt() }.fold(0) { acc, buttonIndex ->
                acc or (1 shl buttonIndex)
            }
        }

    fun parse(input: String): List<Machine> =
        input.lines().map { line ->
            val lineParts = line.split(" ")
            Machine(
                stateWidth = lineParts[0].length - 2,
                expectedLights = parseExpectedLights(lineParts[0]),
                buttons = parseButtons(lineParts.drop(1).dropLast(1)),
            )
        }

    fun part1(input: String): Int = parse(input).sumOf { it.leastButtonPresses().size }

    data class MachineJoltage(
        val expectedState: List<Int>,
        val buttons: List<List<Int>>,
    ) {
        private fun startState(width: Int = expectedState.size) = (0 until width).map { 0 }

        fun pressButtons(pattern: List<Int>): List<Int> =
            pattern.foldIndexed(startState()) { buttonIndex, state, buttonPressCount ->
                state.zip(
                    buttons[buttonIndex].map { it * buttonPressCount },
                ) { stateValue, buttonPressCount -> stateValue + buttonPressCount }
            }

        fun maxPresses() = expectedState.max()

        private val possiblePressPatternMemo = ConcurrentHashMap<Array<Int>, Boolean>()

        fun possiblePressPattern(pattern: List<Int>): Boolean =
            possiblePressPatternMemo.getOrPut(pattern.toTypedArray()) {
                expectedState.zip(pressButtons((0 until buttons.size - pattern.size).map { 0 } + pattern)).all {
                    it.first >= it.second
                }
            }

        fun buttonPressPatterns(length: Int = buttons.size): Sequence<List<Int>> =
            sequence {
                generateSequence(1) { it + 1 }.forEach { yieldAll(buttonPressPatternsForSum(length, it)) }
            }

        fun buttonPressPatternsForSum(
            length: Int = buttons.size,
            sum: Int,
        ): Sequence<List<Int>> =
            sequence {
                if (sum == 0) {
                    yield((0 until length).map { 0 })
                } else if (length == 1) {
                    yield(listOf(sum))
                } else {
                    (0 until maxPresses()).forEach { value ->
                        yieldAll(
                            buttonPressPatternsForSum(
                                length - 1,
                                sum - value,
                            ).map { listOf(value) + it }.filter { possiblePressPattern(it) },
                        )
                    }
                }
            }

        fun leastButtonPresses2(): List<Int> =
            // runBlocking {
            buttonPressPatterns()
                // .flowOn(Dispatchers.Default)
                .map { pressButtons(it) to it }
                //      .toList()
                .find { it.first == expectedState }!!
                .second
        // }
    }

    fun parse2(input: String): List<MachineJoltage> =
        input.lines().map { line ->
            val lineParts = line.split(" ")
            val expectedState = lineParts
                .last()
                .filterNot { it in "{}" }
                .split(",")
                .map { it.toInt() }
            MachineJoltage(
                expectedState = expectedState,
                buttons = lineParts
                    .drop(1)
                    .dropLast(1)
                    .map { buttonSpec ->
                        buttonSpec.filterNot { it in "()" }.split(",").map { it.toInt() }
                    }.map { buttonIndexes ->
                        (0 until expectedState.size).mapIndexed { index, _ -> if (index in buttonIndexes) 1 else 0 }
                    },
            )
        }

    fun part2(input: String): Int =
        parse2(input)
            .mapIndexed { index, machine ->
                System.err.println("Processing $index")
                machine.leastButtonPresses2().sum()
            }.sum()
}
