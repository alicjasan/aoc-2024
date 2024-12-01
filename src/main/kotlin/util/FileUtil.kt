package org.example.util

import java.io.File

fun readTwoColumnsOfNumbers(fileName: String): Pair<List<Int>, List<Int>> {
    val firstColumn = mutableListOf<Int>()
    val secondColumn = mutableListOf<Int>()

    File(ClassLoader.getSystemResource(fileName).toURI()).forEachLine { line ->
        val columns = line.trim().split("\\s+".toRegex())
        if (columns.size == 2) {
            val first = columns[0].toIntOrNull()
            val second = columns[1].toIntOrNull()
            if (first != null && second != null) {
                firstColumn.add(first)
                secondColumn.add(second)
            }
        }
    }

    return Pair(firstColumn, secondColumn)
}