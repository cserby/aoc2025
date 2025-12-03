package org.cserby.aoc2025

object Day2 {
    private fun parse(input: String): List<Pair<Long, Long>> =
        input.split(',').map { entry ->
            entry
                .split('-')
                .take(2)
                .let { it[0].toLong() to it[1].toLong() }
        }

    fun part1(input: String): Long =
        parse(input)
            .flatMap { range ->
                (range.first..range.second).map { it.toString() }.filter { id ->
                    id.length % 2 == 0 &&
                        id.take(id.length / 2) == id.drop(id.length / 2)
                }
            }.fold(0L) { acc, curr -> acc + curr.toLong() }

    private fun part2invalid(id: String): Boolean =
        (1..id.length / 2).fold(false) { soFar, repeatLen ->
            soFar || id.chunked(repeatLen).let { chunkList ->
                chunkList.all { it == chunkList.first() }
            }
        }

    fun part2(input: String): Long =
        parse(input)
            .flatMap { range ->
                (range.first..range.second).map { it.toString() }.filter {
                    part2invalid(it)
                }
            }.fold(0L) { acc, curr -> acc + curr.toLong() }
}
