package org.cserby.aoc2025

object Day6 {
    fun parse(input: String): List<List<String>> =
        input
            .lines()
            .map { line ->
                line.trim().split(Regex("\\s+"))
            }.fold(emptyList()) { acc, curr ->
                curr.mapIndexed { index, string -> acc.getOrElse(index) { emptyList() } + string }
            }

    fun evaluate(calculation: List<String>): Long =
        calculation.let { c ->
            val operand = c.last()
            val numbers = c.dropLast(1).map { it.toLong() }
            when (operand) {
                "*" -> numbers.fold(1) { acc, curr -> acc * curr }
                else -> numbers.fold(0) { acc, curr -> acc + curr }
            }
        }

    fun part1(input: String): Long =
        parse(input).sumOf { calculation ->
            evaluate(calculation)
        }

    fun part2(input: String): Long = -1L
}
