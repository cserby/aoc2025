package org.cserby.aoc2025

object Day11 {
    fun parse(input: String): Map<String, List<String>> =
        input.lines().associate { line ->
            val (from, tos) = line.split(": ")
            from to tos.split(" ")
        }

    val countPathsMemo = mutableMapOf<Pair<String, String>, Long>()

    fun countPathsTo(
        graph: Map<String, List<String>>,
        from: String,
        to: String,
    ): Long =
        graph.getOrDefault(from, emptyList()).let { neighbors ->
            if (to in neighbors) {
                1
            } else if (neighbors.isEmpty()) {
                0
            } else {
                countPathsMemo.getOrPut(from to to) { neighbors.sumOf { countPathsTo(graph, it, to) } }
            }
        }

    fun countPathsToOut(
        graph: Map<String, List<String>>,
        from: String,
    ): Long = countPathsTo(graph, from, "out")

    fun part1(input: String): Long = countPathsToOut(parse(input), "you")

    fun part2(input: String): Long =
        parse(input).let { graph ->
            val fromSvrToDac = countPathsTo(graph, "svr", "dac")
            val fromDacToFft = countPathsTo(graph, "dac", "fft")
            val fromFftToOut = countPathsTo(graph, "fft", "out")
            val fromSvrToFft = countPathsTo(graph, "svr", "fft")
            val fromFftToDac = countPathsTo(graph, "fft", "dac")
            val fromDacToOut = countPathsTo(graph, "dac", "out")

            return fromSvrToFft * fromFftToDac * fromDacToOut + fromSvrToDac * fromDacToFft * fromFftToOut
        }
}
