package org.cserby.aoc2025

object Day11 {
    fun parse(input: String): Map<String, List<String>> =
        input.lines().associate { line ->
            val (from, tos) = line.split(": ")
            from to tos.split(" ")
        }

    val allPathsOutMemo = mutableMapOf<String, Int>()

    fun allPathsToOut(
        graph: Map<String, List<String>>,
        from: String,
    ): Int =
        if ("out" in graph[from]!!) {
            1
        } else {
            allPathsOutMemo.getOrPut(from) { graph[from]!!.sumOf { allPathsToOut(graph, it) } }
        }

    fun part1(input: String): Int = allPathsToOut(parse(input), "you")

    fun part2(input: String): Int = -1
}
