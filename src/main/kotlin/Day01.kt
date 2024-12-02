package org.example

import org.example.util.readTwoColumnsOfNumbers

fun main() {
    val lists = readTwoColumnsOfNumbers("day01")
    println(findDistancesSum(lists))
    println(calculateSimilarityScore(lists.first.sorted(), lists.second.sorted()))
}

fun findDistancesSum(lists: Pair<List<Int>, List<Int>>): Int {
    val firstListSorted = lists.first.sorted()
    val secondListSorted = lists.second.sorted()

    val result = firstListSorted.mapIndexed { index, number ->
        kotlin.math.abs(number - secondListSorted[index])
    }.sum()

    return result
}

fun calculateSimilarityScore(sortedList1: List<Int>, sortedList2: List<Int>): Int {
    return sortedList1.sumOf { element ->
        val count = sortedList2.takeWhile { it <= element }.count { it == element }
        element * count
    }
}

