package org.cserby.aoc2025.test

import org.cserby.aoc2025.Day12
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    @Test
    fun part1rotate() {
        assertEquals(
            Day12.parsePresent(
                "1:\n" +
                    "###\n" +
                    "###\n" +
                    "..#",
            ),
            Day12
                .parsePresent(
                    "4:\n" +
                        "###\n" +
                        "##.\n" +
                        "##.",
                ).rotate90deg(),
        )
    }

    @Test
    fun part1rotateAndFlip() {
        Day12
            .parsePresent(
                "4:\n" +
                    "###\n" +
                    "##.\n" +
                    "##.",
            ).all()
            .forEach {
                println()
                println(it.show())
                println()
            }
    }

    /* In the spirit of the last day, this is a trick puzzle, a super easy
        check results in the correct value for the input (not for the example
        though).
     */
    @Test
    fun part1example() {
        assertEquals(2, Day12.part1(Utils.readFile("/day12.example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(487, Day12.part1(Utils.readFile("/day12.input.txt")))
    }

    @Test
    fun part2example() {
        assertEquals(-1, Day12.part2(Utils.readFile("/day12.example2.txt")))
    }

    @Test
    fun part2() {
        assertEquals(-1, Day12.part2(Utils.readFile("/day12.input.txt")))
    }
}
