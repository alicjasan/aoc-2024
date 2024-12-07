package com.adventofcode

import com.adventofcode.util.readTextLines

fun main() {
    val input = readTextLines("day06")
    val grid = input.flatMapIndexed { y, row ->
        row.mapIndexed { x, char -> Point(x, y) to char }
    }.toMap().withDefault { ' ' }
    val start = grid.entries.first { it.value == '^' }.key
    val initialResult = calculateGuardPositions(null, start, grid)

    println(initialResult.path.size)
    println(
        initialResult.path
            .filterNot { it == start }
            .count { obstacle ->
                calculateGuardPositions(obstacle, start, grid).hasLoop
            })
}

fun calculateGuardPositions(newObstacle: Point? = null, start: Point, grid: Map<Point, Char>): PathResult {
    var currentPosition = start
    var direction = Point.UP
    val visitedPositions = mutableSetOf<Pair<Point, Point>>()

    while (currentPosition in grid && !visitedPositions.contains((currentPosition to direction))) {
        visitedPositions.add(currentPosition to direction)

        val nextPosition = currentPosition + direction
        when {
            grid.getValue(nextPosition) == '#' || nextPosition == newObstacle -> direction = direction.turn()
            else -> currentPosition = nextPosition
        }
    }

    val uniquePath = visitedPositions.map { it.first }.toSet()
    val loopDetected = currentPosition to direction in visitedPositions
    return PathResult(path = uniquePath, hasLoop = loopDetected)
}

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    companion object {
        val UP = Point(0, -1)
        val RIGHT = Point(1, 0)
        val DOWN = Point(0, 1)
        val LEFT = Point(-1, 0)
    }

    fun turn(): Point {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            else -> error("Invalid: $this")
        }
    }
}

data class PathResult(val path: Set<Point>, val hasLoop: Boolean)