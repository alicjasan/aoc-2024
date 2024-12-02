package org.example

import org.example.util.readLinesOfNumbers
import kotlin.math.abs

fun main() {
    val linesOfNumbers = readLinesOfNumbers("day02")
    println(countSafeRecords(linesOfNumbers))
}

private fun findDistance(a: Int, b: Int): Int {
    return abs(a - b)
}

private fun countSafeRecords(linesOfNumbers: List<List<Int>>): Int {
    var safeRecords = 0
    linesOfNumbers.forEach { line ->
        if (isSafe(line) || isSafeWithOneRemoval(line)) {
            safeRecords++
        }
    }
    return safeRecords
}

fun isMonotonic(list: List<Int>): Boolean {
    if (list.size < 2) return true

    val increasing = list.zipWithNext().all { (a, b) -> a <= b }
    val decreasing = list.zipWithNext().all { (a, b) -> a >= b }

    return increasing || decreasing
}

private fun isSafe(line: List<Int>): Boolean {
    return isMonotonic(line) && line.zipWithNext().all { (a, b) ->
        val distance = findDistance(a, b)
        distance in 1..3
    }
}

private fun isSafeWithOneRemoval(line: List<Int>): Boolean {
    return line.indices.any { index ->
        val modifiedLine = line.toMutableList().apply { removeAt(index) }
        isSafe(modifiedLine)
    }
}
