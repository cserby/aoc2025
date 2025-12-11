package org.cserby.aoc2025

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import kotlin.collections.last
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

    fun IntArray.vecMul(by: Int): IntArray = IntArray(this.size) { i -> this[i] * by }

    fun IntArray.vecMinus(other: IntArray) = IntArray(this.size) { i -> this[i] - other[i] }

    fun IntArray.vecAdd(other: IntArray) = IntArray(this.size) { i -> this[i] + other[i] }

    data class MachineJoltage(
        val expectedState: IntArray,
        val buttons: List<IntArray>,
    ) {
        private val startState = IntArray(expectedState.size) { 0 }

        private val minPresses = expectedState.min()

        private fun maxPresses(state: IntArray): Map<IntArray, Int> =
            buttons.associateWith { button ->
                state
                    .zip(button)
                    .filter { it.second != 0 }
                    .minOfOrNull { it.first } ?: 0
            }

        fun pressButtons(pattern: List<Int>): IntArray =
            pattern.foldIndexed(startState) { buttonIndex, stateVector, buttonPressCount ->
                stateVector.vecAdd(buttons[buttonIndex].vecMul(buttonPressCount))
            }

        fun buttonPressPatterns(): Sequence<List<Int>> =
            sequence {
                generateSequence(minPresses) { it + 1 }.forEach { numPresses ->
                    yieldAll(buttonPressPatternsForSum(buttons, numPresses, emptyList()))
                }
            }

        fun buttonPressPatternsForSum(
            buttons: List<IntArray>,
            sum: Int,
            prefix: List<Int>,
            currentState: IntArray = startState,
        ): Sequence<List<Int>> =
            sequence {
                if (sum == 0) {
                    val pattern = prefix + (0 until buttons.size).map { 0 }
                    if (currentState.contentEquals(expectedState)) {
                        yield(pattern)
                    }
                } else if (buttons.size == 1) {
                    if (pressButtons(prefix + sum).contentEquals(expectedState)) {
                        yield(prefix + sum)
                    }
                } else {
                    val reducedState = expectedState.vecMinus(currentState)
                    val button = buttons.first()
                    val maxPresses = maxPresses(reducedState)
                    val maxPressesForButton = maxPresses[button]!!
                    if (reducedState.all { it >= 0 } &&
                        reducedState.min() <= sum &&
                        maxPresses.values.sum() >= sum
                    ) {
                        (min(maxPressesForButton, sum) downTo 0).forEach { value ->
                            yieldAll(
                                buttonPressPatternsForSum(
                                    buttons.drop(1),
                                    sum - value,
                                    prefix + value,
                                    currentState.vecAdd(button.vecMul(value)),
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
                .let { lst -> IntArray(lst.size) { i -> lst[i].toInt() } }
            MachineJoltage(
                expectedState = expectedState,
                buttons = lineParts
                    .drop(1)
                    .dropLast(1)
                    .map { buttonSpec ->
                        buttonSpec.filterNot { it in "()" }.split(",").map { it.toInt() }
                    }.map { buttonIndexes ->
                        IntArray(expectedState.size) { i -> if (i in buttonIndexes) 1 else 0 }
                    }.sortedByDescending { it.size },
            )
        }

    fun part2(input: String): Int =
        runBlocking(Dispatchers.Default) {
            parse2(input)
                .mapIndexed { index, machine ->
                    async {
                        System.err.println("${LocalDateTime.now()} Processing $index")
                        machine.leastButtonPresses2().sum().also {
                            System.err.println("${LocalDateTime.now()} DONE with $index")
                        }
                    }
                }.awaitAll()
                .sum()
        }
}
