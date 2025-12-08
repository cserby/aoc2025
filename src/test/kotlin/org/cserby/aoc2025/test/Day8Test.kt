package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day8
import kotlin.test.Test
import kotlin.test.assertEquals

class Day8Test {
    @Test
    fun part1example() {
        assertEquals(40, Day8.part1(Utils.readFile("/day8.example.txt"), numConnections = 10))
    }

    @Test
    fun part1() {
        // too low
        assertEquals(52668, Day8.part1(Utils.readFile("/day8.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(25272, Day8.part2(Utils.readFile("/day8.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(1474050600, Day8.part2(Utils.readFile("/day8.input.txt")))
    }
}
