package org.cserby.aoc2025

import kotlin.collections.get

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

    fun parse2(input: String): List<List<String>> =
        input.lines().let { lines ->
            val lineLength = lines.maxOf { it.length }
            val operands = lines.last().trim().split(Regex("\\s+"))
            val numbers = lines
                .dropLast(1)
                .map { line -> line.toCharArray() }
                .let { matrix ->
                    List(lineLength) { col ->
                        List(matrix.size) { row ->
                            matrix[row].getOrElse(col) { ' ' }
                        }.joinToString(separator = "").trim()
                    }
                }.joinToString(separator = "\n")
                .split("\n\n")
                .map { line -> line.split("\n") }

            numbers.mapIndexed { idx, numbers -> numbers + operands[idx] }
        }

    fun part2(input: String): Long =
        parse2(input).sumOf { calculation ->
            evaluate(calculation)
        }
}
