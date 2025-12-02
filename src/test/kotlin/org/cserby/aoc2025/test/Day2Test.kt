package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {
    @Test
    fun part1example() {
        assertEquals(1227775554, Day2.part1(Utils.readFile("/day2.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(56660955519, Day2.part1(Utils.readFile("/day2.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(-1, Day2.part2(Utils.readFile("/day2.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(-1, Day2.part2(Utils.readFile("/day2.input.txt")))
    }
}
