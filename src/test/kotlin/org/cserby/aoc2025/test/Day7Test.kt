package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day7
import kotlin.test.Test
import kotlin.test.assertEquals

class Day7Test {
    @Test
    fun part1example() {
        assertEquals(21, Day7.part1(Utils.readFile("/day7.example.txt")))
    }

    @Test
    fun part1() {
        // too high
        assertEquals(1660, Day7.part1(Utils.readFile("/day7.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(40L, Day7.part2(Utils.readFile("/day7.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(305999729392659L, Day7.part2(Utils.readFile("/day7.input.txt")))
    }
}
