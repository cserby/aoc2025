package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day4
import kotlin.test.Test
import kotlin.test.assertEquals

class Day4Test {
    @Test
    fun part1example() {
        assertEquals(13, Day4.part1(Utils.readFile("/day4.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(1604, Day4.part1(Utils.readFile("/day4.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(43, Day4.part2(Utils.readFile("/day4.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(-1, Day4.part2(Utils.readFile("/day4.input.txt")))
    }
}
