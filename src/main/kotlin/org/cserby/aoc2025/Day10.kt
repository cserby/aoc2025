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
import kotlin.math.exp
import kotlin.math.min

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

    fun List<Int>.vecMul(by: Int): List<Int> = this.map { it * by }

    fun List<Int>.vecMinus(other: List<Int>) = this.zip(other) { a, b -> a - b }

    fun List<Int>.vecAdd(other: List<Int>) = this.zip(other) { a, b -> a + b }

    data class MachineJoltage(
        val expectedState: List<Int>,
        val buttons: List<List<Int>>,
    ) {
        private val startState = (0 until expectedState.size).map { 0 }

        private val minPresses = expectedState.min()

        private fun maxPresses(state: List<Int>): Map<List<Int>, Int> =
            buttons.associateWith { button ->
                state
                    .zip(button) { stateValue, buttonAffectsState ->
                        stateValue * buttonAffectsState
                    }.filterNot { it == 0 }
                    .minOrNull() ?: 0
            }

        fun pressButtons(pattern: List<Int>): List<Int> =
            pattern.foldIndexed(startState) { buttonIndex, stateVector, buttonPressCount ->
                stateVector.vecAdd(buttons[buttonIndex].vecMul(buttonPressCount))
            }

        fun possiblePressPatternPrefix(patternPrefix: List<Int>): Boolean =
            pressButtons(patternPrefix).zip(expectedState).let { pairs ->
                pairs.all { it.first <= it.second }
            }

        fun buttonPressPatterns(): Sequence<List<Int>> =
            sequence {
                generateSequence(minPresses) { it + 1 }.forEach { numPresses ->
                    yieldAll(buttonPressPatternsForSum(buttons, numPresses, emptyList()))
                }
            }

        fun buttonPressPatternsForSum(
            buttons: List<List<Int>>,
            sum: Int,
            prefix: List<Int>,
        ): Sequence<List<Int>> =
            sequence {
                if (sum == 0) {
                    val pattern = prefix + (0 until buttons.size).map { 0 }
                    if (pressButtons(pattern) == expectedState) {
                        yield(pattern)
                    }
                } else if (buttons.size == 1) {
                    if (pressButtons(prefix + sum) == expectedState) {
                        yield(prefix + sum)
                    }
                } else {
                    val reducedState = expectedState.vecMinus(pressButtons(prefix))
                    val button = buttons.first()
                    val maxPressesForButton = maxPresses(reducedState)[button]!!
                    (0..min(maxPressesForButton, sum)).forEach { value ->
                        if (possiblePressPatternPrefix(prefix + value)) {
                            yieldAll(
                                buttonPressPatternsForSum(
                                    buttons.drop(1),
                                    sum - value,
                                    prefix + value,
                                ),
                            )
                        }
                    }
                }
            }

        fun leastButtonPresses2(): List<Int> = buttonPressPatterns().first()
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
