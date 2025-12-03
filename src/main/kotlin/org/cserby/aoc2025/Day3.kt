package org.cserby.aoc2025

object Day3 {
    fun parse(input: String): List<List<Int>> = input.split("\n").map { line -> line.toCharArray().toList().map { it.digitToInt() } }

    fun maxJoltage(bank: List<Int>): Int =
        bank
            .flatMapIndexed { index, curr ->
                bank.drop(index + 1).map { curr2 -> curr to curr2 }
            }.maxOf { 10 * it.first + it.second }

    fun part1(input: String): Int = parse(input).map { maxJoltage(it) }.fold(0) { acc, curr -> acc + curr }

    fun part2(input: String): Int = -1
}
