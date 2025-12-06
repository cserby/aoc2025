package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day6
import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
    @Test
    fun part1example() {
        assertEquals(4277556L, Day6.part1(Utils.readFile("/day6.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(6957525317641L, Day6.part1(Utils.readFile("/day6.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(3263827L, Day6.part2(Utils.readFile("/day6.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(13215665360076L, Day6.part2(Utils.readFile("/day6.input.txt")))
    }
}
