package com.adventofcode.util

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

fun readLinesOfNumbers(fileName: String): List<List<Int>> {
    val list = mutableListOf<List<Int>>()

    val fileUrl = ClassLoader.getSystemResource(fileName)
        ?: throw IllegalArgumentException("File not found: $fileName")

    File(fileUrl.toURI()).forEachLine { line ->
        val numbers = line.trim().split("\\s+".toRegex()).map { it.toInt() }
        list.add(numbers)
    }

    return list
}

fun readText(fileName: String): String = ClassLoader.getSystemResource(fileName).readText()

fun readTextLines(fileName: String): List<String> =
    File(ClassLoader.getSystemResource(fileName).toURI()).readLines()
