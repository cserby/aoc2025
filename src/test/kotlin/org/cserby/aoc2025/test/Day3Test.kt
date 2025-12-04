package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day3
import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test {
    @Test
    fun part1maxJoltage() {
        assertEquals(89, Day3.maxJoltage(Day3.parse("811111111111119")[0]))
    }

    @Test
    fun part1example() {
        assertEquals(357, Day3.part1(Utils.readFile("/day3.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(17524, Day3.part1(Utils.readFile("/day3.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(3121910778619, Day3.part2(Utils.readFile("/day3.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(173848577117276, Day3.part2(Utils.readFile("/day3.input.txt")))
    }
}
