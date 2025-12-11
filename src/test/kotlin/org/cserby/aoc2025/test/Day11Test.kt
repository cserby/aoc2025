package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day11
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    @Test
    fun part1example() {
        assertEquals(5, Day11.part1(Utils.readFile("/day11.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(615, Day11.part1(Utils.readFile("/day11.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(-1, Day11.part2(Utils.readFile("/day11.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(-1, Day11.part2(Utils.readFile("/day11.input.txt")))
    }
}
