package org.cserby.aoc2025

object Utils {
    fun <T> allPairs(lst: List<T>): List<Pair<T, T>> =
        lst.flatMapIndexed { index, first ->
            lst.drop(index).map { first to it }
        }
}
