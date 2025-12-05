package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day5
import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Test {
    @Test
    fun part1example() {
        assertEquals(3, Day5.part1(Utils.readFile("/day5.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(661, Day5.part1(Utils.readFile("/day5.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(14L, Day5.part2(Utils.readFile("/day5.example.txt")))
    }

    @Test
    fun part2intersect() {
        val rangeA = 1000L..1020L

        // rangeA left of rangeB
        assertEquals(listOf(rangeA), Day5.intersect(rangeA, 1021L..1030L))

        // rangeA right of rangeB
        assertEquals(listOf(rangeA), Day5.intersect(rangeA, 990L..999L))

        // rangeB overlaps rangeA
        assertEquals(emptyList(), Day5.intersect(rangeA, 990L..1030L))
        assertEquals(emptyList(), Day5.intersect(rangeA, 1000L..1020L))

        // rangeB inside rangeA
        assertEquals(listOf(1000L..1004L, 1016L..1020L), Day5.intersect(rangeA, 1005L..1015L))

        // rangeB cuts into rangeA from the left
        assertEquals(listOf(1006L..1020L), Day5.intersect(rangeA, 990L..1005L))
        assertEquals(listOf(1006L..1020L), Day5.intersect(rangeA, 1000L..1005L))
        assertEquals(listOf(1001L..1020L), Day5.intersect(rangeA, 990L..1000L))
        assertEquals(listOf(1001L..1020L), Day5.intersect(rangeA, 1000L..1000L))

        // rangeB cuts into rangeA from the right
        assertEquals(listOf(1000L..1019L), Day5.intersect(rangeA, 1020L..1025L))
        assertEquals(listOf(1000L..1019L), Day5.intersect(rangeA, 1020L..1020L))
        assertEquals(listOf(1000L..1000L), Day5.intersect(rangeA, 1001L..1050L))
    }

    @Test
    fun part2() {
        assertEquals(359526404143208L, Day5.part2(Utils.readFile("/day5.input.txt")))
    }
}
