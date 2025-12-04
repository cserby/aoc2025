package org.cserby.aoc2025

object Day3 {
    fun parse(input: String): List<List<Int>> = input.split("\n").map { line -> line.toCharArray().toList().map { it.digitToInt() } }

    fun maxJoltage(bank: List<Int>): Int =
        bank
            .flatMapIndexed { index, curr ->
                bank.drop(index + 1).map { curr2 -> curr to curr2 }
            }.maxOf { 10 * it.first + it.second }

    fun part1(input: String): Int = parse(input).sumOf { maxJoltage(it) }

    private val memo: HashMap<String, List<Int>> = HashMap()

    private fun listToLong(list: List<Int>): Long = list.joinToString(separator = "").toLong()

    fun maxJoltage2(
        bank: List<Int>,
        digits: Int = 12,
        acc: List<Int> = emptyList(),
    ): List<Int> =
        when (digits) {
            0 -> acc

            1 -> acc + bank.max()

            else -> if (digits > bank.size) {
                emptyList()
            } else if (digits == bank.size) {
                acc + bank
            } else {
                memo.getOrPut("$digits-from-${bank.joinToString(separator = "")}") {
                    val selectCurrent = acc + bank.first() + maxJoltage2(bank.drop(1), digits - 1)
                    val dontSelectCurrent = acc + maxJoltage2(bank.drop(1), digits)
                    if (listToLong(selectCurrent) > listToLong(dontSelectCurrent)) selectCurrent else dontSelectCurrent
                }
            }
        }

    fun part2(input: String): Long = parse(input).sumOf { listToLong(maxJoltage2(it)) }
}
