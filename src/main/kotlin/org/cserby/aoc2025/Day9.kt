package org.cserby.aoc2025

import kotlin.math.abs

object Day9 {
    fun parse(input: String): List<Pair<Long, Long>> =
        input.lines().map {
            it.split(",").let { coords ->
                coords[0].toLong() to coords[1].toLong()
            }
        }

    fun rectangleArea(
        first: Pair<Long, Long>,
        second: Pair<Long, Long>,
    ): Long = (abs(first.first - second.first) + 1) * (abs(first.second - second.second) + 1)

    fun part1(input: String): Long = Utils.allPairs(parse(input)).maxOf { rectangleArea(it.first, it.second) }

    fun part2(input: String): Long = -1L
}
