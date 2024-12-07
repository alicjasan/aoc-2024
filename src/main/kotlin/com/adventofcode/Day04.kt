package com.adventofcode

import com.adventofcode.util.readTextLines

fun main() {
    val word = "XMAS"
    val textLines = readTextLines("day04")
    println(countWordInGrid(textLines.map { it.toCharArray() }.toTypedArray(), word))
    println(countXMASPatterns(textLines))
}

fun countWordInGrid(grid: Array<CharArray>, word: String): Int {
    val rows = grid.size
    val cols = grid[0].size
    val wordLength = word.length

    fun isValid(x: Int, y: Int): Boolean {
        return x in 0 until rows && y in 0 until cols
    }

    fun checkDirection(x: Int, y: Int, dx: Int, dy: Int): Boolean {
        for (i in 0 until wordLength) {
            val nx = x + i * dx
            val ny = y + i * dy
            if (!isValid(nx, ny) || grid[nx][ny] != word[i]) {
                return false
            }
        }
        return true
    }

    var count = 0
    (0 until rows).forEach { x ->
        (0 until cols).forEach { y ->
            count += directions.count { (dx, dy) -> checkDirection(x, y, dx, dy) }
        }
    }
    return count
}

fun countXMASPatterns(input: List<String>): Int {
    val validPatterns = setOf("MMSS", "MSSM", "SSMM", "SMMS")

    fun List<String>.safeCharAt(x: Int, y: Int): Char =
        if (y in indices && x in this[y].indices) this[y][x] else ' '

    return input.flatMapIndexed { y, row ->
        row.mapIndexed { x, c ->
            if (c == 'A') {
                // Build the string of the "corners" around the current position
                corners.joinToString("") { (dx, dy) ->
                    input.safeCharAt(x + dx, y + dy).toString()
                } in validPatterns
            } else false
        }
    }.count { it }
}

val directions = listOf(
    -1 to -1, -1 to 0, -1 to 1,
    0 to -1, 0 to 1,
    1 to -1, 1 to 0, 1 to 1
)
val corners = listOf(-1 to -1, -1 to 1, 1 to 1, 1 to -1)