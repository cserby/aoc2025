package org.cserby.aoc2025

object Day1 {
    private enum class Direction(
        dir: String,
    ) {
        R("R"),
        L("L"),
    }

    private data class Instruction(
        val direction: Direction,
        val steps: Int,
    )

    private fun parse(lines: List<String>): List<Instruction> =
        lines.map { line ->
            Instruction(
                direction = Direction.valueOf(line.take(1)),
                steps = line.drop(1).toInt(),
            )
        }

    private fun perform(
        state: Int,
        instruction: Instruction,
    ): Int =
        when (instruction.direction) {
            Direction.L -> if (state > instruction.steps) (state - instruction.steps) else (state + 100 - instruction.steps)
            Direction.R -> state + instruction.steps
        } % 100

    fun part1(
        input: String,
        startAt: Int = 50,
    ): Int =
        parse(input.lines())
            .fold(Pair(startAt, 0)) { (state, zeroes), instruction ->
                val newState = perform(state, instruction)
                Pair(newState, if (newState == 0) zeroes + 1 else zeroes)
            }.second

    private fun clicks(
        state: Int,
        instruction: Instruction,
    ) = sequence {
        when (instruction.direction) {
            Direction.L -> {
                (0 until instruction.steps).forEach { step ->
                    yield((state - step) % 100)
                }
            }

            Direction.R -> {
                (0 until instruction.steps).forEach { step ->
                    yield((state + step) % 100)
                }
            }
        }
    }

    fun part2(
        input: String,
        startAt: Int = 50,
    ): Int =
        parse(input.lines())
            .fold(Pair(startAt, 0)) { (state, zeroes), instruction ->
                val stepSequence = clicks(state, instruction)
                val newZeroes = stepSequence.count { it == 0 }
                val newState = perform(state, instruction)
                Pair(newState, zeroes + newZeroes)
            }.second
}
