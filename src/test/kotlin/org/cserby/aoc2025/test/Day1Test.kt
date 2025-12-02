package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day1
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {
    @Test
    fun part1example() {
        assertEquals(3, Day1.part1(Utils.readFile("/day1.example.txt"), startAt = 50))
    }

    @Test
    fun part1() {
        assertEquals(1078, Day1.part1(Utils.readFile("/day1.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(6, Day1.part2(Utils.readFile("/day1.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(6412, Day1.part2(Utils.readFile("/day1.input.txt")))
    }
}
