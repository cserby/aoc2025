package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day10
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {
    @Test
    fun part1parseLights() {
        assertEquals(0b0110, Day10.parseExpectedLights("[.##.]"))
        assertEquals(0b101110, Day10.parseExpectedLights("[.###.#]"))
    }

    @Test
    fun part1parse() {
        assertEquals(
            listOf(Day10.Machine(4, 6, listOf(0b1000, 0b1010, 0b100, 0b1100, 0b101, 0b11))),
            Day10.parse("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"),
        )
    }

    @Test
    fun part1example() {
        assertEquals(7, Day10.part1(Utils.readFile("/day10.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(481, Day10.part1(Utils.readFile("/day10.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(33, Day10.part2(Utils.readFile("/day10.example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(-1, Day10.part2(Utils.readFile("/day10.input.txt")))
    }
}
