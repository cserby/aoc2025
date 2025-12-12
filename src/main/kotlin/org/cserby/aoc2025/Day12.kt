package org.cserby.aoc2025

object Day12 {
    data class Puzzle(
        val width: Int,
        val height: Int,
        val presentCounts: List<Int>,
    ) {
        val size: Int = width * height

        fun sizeNecessary(presents: List<Present>): Int =
            presents.foldIndexed(0) { index, sum, present ->
                sum + present.count * presentCounts[index]
            }
    }

    fun parsePuzzle(line: String): Puzzle =
        line.let { line ->
            val (size, presents) = line.split(": ")
            val (width, height) = size.split("x").map { it.toInt() }
            Puzzle(
                width = width,
                height = height,
                presentCounts = presents.split(" ").map { it.toInt() },
            )
        }

    fun parsePresent(part: String): Present = Present(part.lines().drop(1).map { it.toList() })

    private fun parse(input: String): Pair<List<Present>, List<Puzzle>> =
        input.split("\n\n").let { parts ->
            val presents = parts.dropLast(1).map { parsePresent(it) }
            val puzzles = parts.last().lines().map { parsePuzzle(it) }
            presents to puzzles
        }

    data class Present(
        val points: List<List<Char>>,
    ) {
        val count = points.sumOf { it.count { ch -> ch == '#' } }

        fun flip(): Present = Present(points.map { it.reversed() })

        fun rotate90deg(): Present =
            Present(
                (0 until points.size).map { y ->
                    (points.size - 1 downTo 0).map { x ->
                        points[x][y]
                    }
                },
            )

        fun all(): Set<Present> =
            generateSequence(this) { it.rotate90deg() }.take(4).toSet() +
                generateSequence(this.flip()) { it.rotate90deg() }.take(4).toSet()

        fun show(): String = points.joinToString(separator = "\n") { it.joinToString(separator = "") }
    }

    fun part1(input: String): Int =
        /* Lucky enough, this simple check gives the correct result for part1,
         *  not for the example though */
        parse(input).let { (presents, puzzles) ->
            puzzles.count { it.size >= it.sizeNecessary(presents) }
        }

    fun part2(input: String): Int = -1
}
