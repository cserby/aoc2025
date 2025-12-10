package org.cserby.aoc2025

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
        tailrec fun leastButtonPresses(
            toCheckButtonIndexSequences: List<Pair<List<Int>, List<Int>>> = (0 until buttons.size).map {
                listOf(it) to
                    (0 until expectedState.size).map { 0 }
            },
            visitedStates: List<List<Int>> = listOf((0 until expectedState.size).map { 0 }),
        ): List<Int> {
            val (sequence, prevState) = toCheckButtonIndexSequences.first()
            val newState = prevState.mapIndexed { index, prevBulbState ->
                if (index in
                    buttons[sequence.last()]
                ) {
                    prevBulbState + 1
                } else {
                    prevBulbState
                }
            }
            return when (newState) {
                expectedState -> {
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
                buttons = lineParts.drop(1).dropLast(1).map { buttonSpec ->
                    buttonSpec.filterNot { it in "()" }.split(",").map { it.toInt() }
                },
            )
        }

    fun part2(input: String): Int = parse2(input).sumOf { it.leastButtonPresses().size }
}
