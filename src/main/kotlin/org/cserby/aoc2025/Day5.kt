package org.cserby.aoc2025

import kotlin.ranges.rangeTo

object Day5 {
    private fun parse(input: String): Pair<List<LongRange>, List<Long>> =
        input.split("\n").let { lines ->
            val emptyLineIndex = lines.indexOfFirst { it == "" }
            parseFreshIngredientRanges(lines.take(emptyLineIndex)) to lines.drop(emptyLineIndex + 1).map { it.toLong() }
        }

    private fun parseFreshIngredientRanges(lines: List<String>): List<LongRange> =
        lines.map { line ->
            line.split("-", limit = 2).let { parts ->
                parts[0].toLong()..parts[1].toLong()
            }
        }

    fun part1(input: String): Int =
        parse(input).let { (freshRanges, ingredients) ->
            ingredients.count { ingredient -> freshRanges.any { ingredient in it } }
        }

    fun intersect(
        rangeA: LongRange,
        rangeB: LongRange,
    ): List<LongRange> =
        if (rangeA.last < rangeB.first) { // rangeA left of rangeB
            listOf(rangeA)
        } else if (rangeA.first > rangeB.last) { // rangeA right of rangeB
            listOf(rangeA)
        } else if (rangeB.first <= rangeA.first && rangeB.last >= rangeA.last) { // rangeB overlaps rangeA
            emptyList()
        } else if (rangeB.first > rangeA.first && rangeB.last < rangeA.last) { // rangeB inside rangeA
            listOf(rangeA.first until rangeB.first, rangeB.last + 1..rangeA.last)
        } else if (rangeB.first <= rangeA.first) { // rangeB cuts into rangeA from the left
            listOf(rangeB.last + 1..rangeA.last)
        } else { // rangeB cuts into rangeA from the right
            listOf(rangeA.first until rangeB.first)
        }

    tailrec fun intersectAll(
        rangeAs: List<LongRange>,
        rangeBs: List<LongRange>,
    ): List<LongRange> =
        if (rangeBs.isEmpty()) {
            rangeAs
        } else {
            val rangeB = rangeBs.first()
            val newRangeAs = rangeAs.flatMap { intersect(it, rangeB) }
            intersectAll(newRangeAs, rangeBs.drop(1))
        }

    fun part2(input: String): Long =
        parse(input).let { (freshRanges, _) ->
            freshRanges
                .flatMapIndexed { index, range ->
                    val remaining = freshRanges.drop(index + 1)
                    intersectAll(listOf(range), remaining)
                }.sumOf { it.last - it.first + 1 }
        }
}
