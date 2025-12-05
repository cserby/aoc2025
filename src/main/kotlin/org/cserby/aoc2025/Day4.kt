package org.cserby.aoc2025

object Day4 {
    private fun parse(input: String): List<List<Char>> = input.split("\n").map { it.toCharArray().toList() }

    private fun neighbors(
        coords: Pair<Int, Int>,
        size: Pair<Int, Int>,
    ): Sequence<Pair<Int, Int>> =
        sequence {
            val (x, y) = coords
            val (maxX, maxY) = size
            (x - 1..x + 1).forEach { currX ->
                (y - 1..y + 1).forEach { currY ->
                    if (currX in 0 until maxX && currY in 0 until maxY && ((x - currX) != 0 || (y - currY) != 0)) {
                        yield(currX to currY)
                    }
                }
            }
        }

    private fun removable(
        coords: Pair<Int, Int>,
        grid: List<List<Char>>,
    ): Boolean =
        grid[coords.first][coords.second] == '@' &&
            (neighbors(coords, grid.size to grid[0].size).count { grid[it.first][it.second] == '@' } < 4)

    fun part1(input: String): Int =
        parse(input)
            .let { grid ->
                (0 until grid.size).flatMap { x ->
                    (0 until grid[0].size).map { y ->
                        if (removable(x to y, grid)) {
                            1
                        } else {
                            0
                        }
                    }
                }
            }.sum()

    private fun remove(grid: List<List<Char>>): List<List<Char>> =
        grid.mapIndexed { x, row ->
            row.mapIndexed { y, cell ->
                if (removable(x to y, grid)) {
                    '.'
                } else {
                    cell
                }
            }
        }

    private fun countRolls(grid: List<List<Char>>): Int = grid.sumOf { row -> row.count { it == '@' } }

    private fun removals(grid: List<List<Char>>) =
        generateSequence(grid to 0) {
            val (grid, _) = it
            val removed = remove(grid)
            removed to countRolls(grid) - countRolls(removed)
        }

    fun part2(input: String): Int = removals(parse(input)).drop(1).takeWhile { it.second != 0 }.sumOf { it.second }
}
