package org.cserby.aoc2025

object Day8 {
    data class JunctionBox(
        val x: Long,
        val y: Long,
        val z: Long,
    ) {
        fun distance(other: JunctionBox): Long =
            (other.x - x) * (other.x - x) + (other.y - y) * (other.y - y) + (other.z - z) * (other.z - z)
    }

    fun parse(input: String): List<JunctionBox> =
        input.split("\n").map { line ->
            line.split(",").map { it.toLong() }.let { coords ->
                JunctionBox(coords[0], coords[1], coords[2])
            }
        }

    fun <T> allPairs(lst: List<T>): List<Pair<T, T>> =
        lst.flatMapIndexed { index, first ->
            lst.drop(index).map { first to it }
        }

    fun distances(junctionBoxes: List<JunctionBox>): List<Pair<Pair<JunctionBox, JunctionBox>, Long>> =
        allPairs(junctionBoxes)
            .map { (first, second) -> (first to second) to first.distance(second) }
            .filterNot { it.first.first == it.first.second }
            .sortedBy { it.second }

    fun mergeCircuits(
        circuits: Set<Set<JunctionBox>>,
        firstCircuit: Set<JunctionBox>,
        secondCircuit: Set<JunctionBox>,
    ): Set<Set<JunctionBox>> =
        circuits.minus(setOf(firstCircuit, secondCircuit)).let {
            val merged = (firstCircuit + secondCircuit)
            it + setOf(merged)
        }

    tailrec fun connect(
        circuits: Set<Set<JunctionBox>>,
        connections: List<Pair<Pair<JunctionBox, JunctionBox>, Long>>,
    ): Set<Set<JunctionBox>> =
        if (connections.isEmpty()) {
            circuits
        } else {
            val newConnection = connections.first()
            val firstCircuit = circuits.find { newConnection.first.first in it }!!
            val secondCircuit = circuits.find { newConnection.first.second in it }!!
            connect(mergeCircuits(circuits, firstCircuit, secondCircuit), connections.drop(1))
        }

    fun part1(
        input: String,
        numConnections: Int = 1000,
    ): Long =
        parse(input).let { junctionBoxes ->
            connect(
                junctionBoxes
                    .map {
                        setOf(it)
                    }.toSet(),
                distances(junctionBoxes).take(numConnections),
            ).map { it.size }.sortedDescending().take(3).fold(1) { acc, curr ->
                acc * curr
            }
        }

    fun part2(input: String): Int = -1
}
