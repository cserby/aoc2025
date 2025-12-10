package org.cserby.aoc2025

import kotlin.math.max
import kotlin.math.min

object Day9 {
    fun parse(input: List<String>): List<Pair<Long, Long>> =
        input.map {
            it.split(",").let { coords ->
                coords[0].toLong() to coords[1].toLong()
            }
        }

    fun rectangleArea(
        first: Pair<Long, Long>,
        second: Pair<Long, Long>,
    ): Long =
        (max(first.first, second.first) - min(first.first, second.first) + 1) *
            (max(first.second, second.second) - min(first.second, second.second) + 1)

    fun maxRectangle(rectangles: List<Pair<Pair<Long, Long>, Pair<Long, Long>>>): Long =
        rectangles.maxOf { rectangleArea(it.first, it.second) }

    fun part1(input: String): Long = maxRectangle(Utils.allPairs(parse(input.lines())))

    fun draw(points: List<Pair<Long, Long>>): String =
        """<svg viewBox="0 0 10 10" xmlns="http://www.w3.org/2000/svg">
             <polygon points="${
            points.joinToString(separator = " ") { "${it.first},${it.second}" }
        }" />
           </svg>
        """.trimIndent()

    fun pointInsideRectangle(
        point: Pair<Long, Long>,
        rectangle: Pair<Pair<Long, Long>, Pair<Long, Long>>,
    ): Boolean =
        point != rectangle.first && point != rectangle.second &&
            min(rectangle.first.first, rectangle.second.first) < point.first &&
            max(rectangle.first.first, rectangle.second.first) > point.first &&
            min(rectangle.first.second, rectangle.second.second) < point.second &&
            max(rectangle.first.second, rectangle.second.second) > point.second

    fun rectanglesInside(points: List<Pair<Long, Long>>): List<Pair<Pair<Long, Long>, Pair<Long, Long>>> =
        Utils.allPairs(points).filterNot { (first, second) ->
            points.any { pointInsideRectangle(it, first to second) }
        }

    fun part2(input: String): Long {
        val inputLines = input.lines()
        // Use draw to render to SVG, split the polygon into two less concave parts
        val topHalf = parse(inputLines.take(249))
        val bottomHalf = parse(inputLines.drop(249))

        return max(
            maxRectangle(rectanglesInside(topHalf)),
            maxRectangle(rectanglesInside(bottomHalf)),
        )
    }
}
