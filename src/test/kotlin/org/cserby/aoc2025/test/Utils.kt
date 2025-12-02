package org.cserby.aoc2025.test

object Utils {
    fun readFile(resourcePath: String): String {
        return javaClass.getResource(resourcePath)?.readText()!!.trim()
    }
}
