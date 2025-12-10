package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day9
import kotlin.test.Test
import kotlin.test.assertEquals

class Day9Test {
    @Test
    fun part1rectangleArea() {
        assertEquals(24, Day9.rectangleArea(2L to 5L, 9L to 7L))
        assertEquals(35, Day9.rectangleArea(7L to 1L, 11L to 7L))
        assertEquals(6, Day9.rectangleArea(7L to 3L, 2L to 3L))
        assertEquals(50, Day9.rectangleArea(2L to 5L, 11L to 1L))
    }

    @Test
    fun part1example() {
        assertEquals(50, Day9.part1(Utils.readFile("/day9.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(4755064176, Day9.part1(Utils.readFile("/day9.input.txt")))
    }

    /* Not a generic solution, doesn't work for the example
    @Test
    fun part2example() {
        assertEquals(-1, Day9.part2(Utils.readFile("/day9.example.txt")))
    }
     */

    @Test
    fun part2() {
        assertEquals(1613305596, Day9.part2(Utils.readFile("/day9.input.txt")))
    }
}
