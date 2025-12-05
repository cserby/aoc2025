package org.cserby.aoc2025

object Day5 {
    private fun parse(input: String): Pair<List<Pair<Long, Long>>, List<Long>> =
        input.split("\n").let { lines ->
            val emptyLineIndex = lines.indexOfFirst { it == "" }
            parseFreshIngredientRanges(lines.take(emptyLineIndex)) to lines.drop(emptyLineIndex + 1).map { it.toLong() }
        }

    private fun parseFreshIngredientRanges(lines: List<String>): List<Pair<Long, Long>> =
        lines.map { line ->
            line.split("-", limit = 2).let { parts ->
                parts[0].toLong() to parts[1].toLong()
            }
        }

    fun part1(input: String): Int =
        parse(input).let { (freshRanges, ingredients) ->
            ingredients.count { ingredient -> freshRanges.any { ingredient in it.first..it.second } }
        }

    fun part2(input: String): Int = -1
}
