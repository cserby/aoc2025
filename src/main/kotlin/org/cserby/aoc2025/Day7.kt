package org.cserby.aoc2025

object Day7 {
    enum class Direction {
        DOWN,
    }

    data class TachionBeam(
        val start: Pair<Int, Int>,
        val direction: Direction = Direction.DOWN,
    ) {
        fun path(field: List<List<Char>>): List<Pair<Int, Int>> =
            generateSequence(start) { (currX, currY) ->
                when (direction) {
                    Direction.DOWN -> if (currX + 1 >= field.size) {
                        return@generateSequence null
                    } else {
                        when (field[currX][currY]) {
                            '^' -> return@generateSequence null
                            else -> currX + 1 to currY
                        }
                    }
                }
            }.toList()
    }

    fun parse(input: String): Pair<List<TachionBeam>, List<List<Char>>> =
        input.split("\n").map { it.toCharArray().toList() }.let { charField ->
            listOf(TachionBeam(0 to charField[0].indexOfFirst { it == 'S' })) to charField
        }

    fun splitterNeighbors(
        point: Pair<Int, Int>,
        field: List<List<Char>>,
    ): List<Pair<Int, Int>> =
        sequence {
            if (point.second > 0) yield(point.first to point.second - 1)
            if (point.second + 1 < field[0].size) yield(point.first to point.second + 1)
        }.toList()

    tailrec fun trace(
        beams: List<TachionBeam>,
        field: List<List<Char>>,
        finishedBeams: List<TachionBeam> = emptyList(),
        tracedPoints: Set<Pair<Int, Int>> = emptySet(),
    ): List<TachionBeam> {
        if (beams.isEmpty()) return finishedBeams
        val beamToTrace = beams.minBy { it.start.first }

        if (beamToTrace.start in tracedPoints) return trace(beams.minus(beamToTrace), field, finishedBeams, tracedPoints)

        val tracedPath = beamToTrace.path(field)
        val (beamEndX, beamEndY) = tracedPath.last()
        val newTracedPoints = tracedPoints + tracedPath.toSet()

        return if (field[beamEndX][beamEndY] !=
            '^'
        ) { // trace exited on the bottom
            trace(beams.minus(beamToTrace), field, finishedBeams + beamToTrace, newTracedPoints)
        } else { // '^'
            trace(
                beams.minus(beamToTrace) +
                    splitterNeighbors(beamEndX to beamEndY, field).filter { it !in newTracedPoints }.map { TachionBeam(it) }.toSet(),
                field,
                finishedBeams + beamToTrace,
                newTracedPoints,
            )
        }
    }

    fun part1(input: String): Int =
        parse(input).let { (beams, field) ->
            val tracedBeams = trace(beams, field)
            tracedBeams.size - tracedBeams.count { it.path(field).last().first == field.size - 1 }
        }

    fun part2(input: String): Long = -1
}
