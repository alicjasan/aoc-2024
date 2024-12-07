package com.adventofcode

import com.adventofcode.util.readTextLines

fun main() {
    val inputs = readTextLines("day07")
    val validSum = inputs
        .mapNotNull { input ->
            val parts = input.split(":")
            val number = parts.getOrNull(0)?.toLongOrNull()
            val values = parts.getOrNull(1)
                ?.split(" ")
                ?.mapNotNull { it.toLongOrNull() }
                ?: emptyList()

            number?.takeIf { calculate(it, values) != null }
        }
        .sum()

    println(validSum)

}

fun calculate(result: Long, numbers: List<Long>): Long? {
    if (numbers.isEmpty()) return null

    fun check(index: Int, current: Long): Long? {
        if (index == numbers.size) return if (current == result) current else null

        val nextValue = numbers[index]
        return check(index + 1, current + nextValue) ?:
        check(index + 1, current * nextValue) ?:
        check(index + 1, concatenate(current, nextValue))
    }

    return check(1, numbers[0])
}

fun concatenate(left: Long, right: Long): Long {
    return "$left$right".toLong()
}


