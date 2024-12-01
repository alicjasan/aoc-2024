package org.example

import org.example.util.readTwoColumnsOfNumbers

fun main() {
    val lists = readTwoColumnsOfNumbers("day1")
    val firstListSorted = lists.first.sorted()
    val secondListSorted = lists.second.sorted()

    val result = firstListSorted.mapIndexed { index, number ->
       kotlin.math.abs(number - secondListSorted[index])
    }.sum()

    println(result)
}
