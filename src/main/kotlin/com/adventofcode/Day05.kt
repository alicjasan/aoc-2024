package com.adventofcode

import com.adventofcode.util.readLinesOfNumbers
import java.io.File

fun main() {
    val rules = findRules("day05", "\\|")
    val numbers = readLinesOfNumbers("day05_1", ",")

    println(numbers.filter { (isValidOrder(rules, it)) }.sumOf { it[it.size / 2] })
    println(numbers.filter { (!isValidOrder(rules, it)) }.map { topologicalSort(rules, it) }.sumOf { it[it.size / 2] })
}

fun findRules(fileName: String, splitBy: String): Map<Int, List<Int>> {
    val resultMap = mutableMapOf<Int, MutableList<Int>>()
    val file = File(ClassLoader.getSystemResource(fileName).toURI())

    for (line in file.readLines()) {
        if (line.isBlank()) break

        val columns = line.trim().split(splitBy.toRegex())
        if (columns.size == 2) {
            val key = columns[0].toIntOrNull()
            val value = columns[1].toIntOrNull()

            if (key != null && value != null) {
                resultMap.computeIfAbsent(key) { mutableListOf() }.add(value)
            }
        }
    }

    return resultMap.mapValues { it.value.toList() }
}

fun isValidOrder(map: Map<Int, List<Int>>, order: List<Int>): Boolean {
    val inDegree = mutableMapOf<Int, Int>()
    val graph = mutableMapOf<Int, MutableList<Int>>()
    val orderSet = order.toSet()

    map.forEach { (key, values) ->
        if (key in orderSet) {
            graph.putIfAbsent(key, mutableListOf())
            inDegree.putIfAbsent(key, 0)
            for (value in values) {
                if (value in orderSet) {
                    graph[key]!!.add(value)
                    inDegree[value] = inDegree.getOrDefault(value, 0) + 1
                }
            }
        }
    }

    val queue = ArrayDeque<Int>()
    for ((node, degree) in inDegree) {
        if (degree == 0) queue.add(node)
    }

    var index = 0
    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        if (index >= order.size || node != order[index]) {
            return false // Mismatch in order
        }
        index++

        for (neighbor in graph[node] ?: emptyList()) {
            inDegree[neighbor] = inDegree[neighbor]!! - 1
            if (inDegree[neighbor] == 0) queue.add(neighbor)
        }
    }

    return index == order.size
}

fun topologicalSort(map: Map<Int, List<Int>>, sequence: List<Int>): List<Int> {
    val inDegree = mutableMapOf<Int, Int>()
    val graph = mutableMapOf<Int, MutableList<Int>>()
    val sequenceSet = sequence.toSet() // For quick lookup

    map.forEach { (key, values) ->
        if (key in sequenceSet) {
            graph.putIfAbsent(key, mutableListOf())
            inDegree.putIfAbsent(key, 0)
            for (value in values) {
                if (value in sequenceSet) {
                    graph[key]!!.add(value)
                    inDegree[value] = inDegree.getOrDefault(value, 0) + 1
                }
            }
        }
    }

    val queue = ArrayDeque<Int>()
    for ((node, degree) in inDegree) {
        if (degree == 0) queue.add(node)
    }

    val sortedOrder = mutableListOf<Int>()
    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        sortedOrder.add(node)

        for (neighbor in graph[node] ?: emptyList()) {
            inDegree[neighbor] = inDegree[neighbor]!! - 1
            if (inDegree[neighbor] == 0) queue.add(neighbor)
        }
    }
    return sortedOrder
}