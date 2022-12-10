package com.aleksandarpetrovski.aoc2022.day09

import com.aleksandarpetrovski.aoc2022.readInputLines
import kotlin.math.abs

fun main() {
    val inputs = readInputLines(day = 9)

    println("Result Part 01: ${numberOfTailPositions(inputs, 2)}")
    println("Result Part 02: ${numberOfTailPositions(inputs, 10)}")
}

fun numberOfTailPositions(movements: List<String>, numberOfKnots: Int): Int {
    val tailPositions = mutableSetOf(Pair(0, 0))
    val knots = (1..numberOfKnots).map { Pair(0, 0) }.toMutableList()

    movements.forEach { movement ->
        val (direction, distance) = movement.split(" ")
        repeat(distance.toInt()) {
            moveKnots(knots, direction)
            tailPositions.add(knots.last())
        }
    }

    return tailPositions.size
}

fun moveKnots(knots: MutableList<Pair<Int, Int>>, direction: String) {

    // move first knot
    when (direction) {
        "R" -> knots[0] = Pair(knots[0].first + 1, knots[0].second)
        "L" -> knots[0] = Pair(knots[0].first - 1, knots[0].second)
        "U" -> knots[0] = Pair(knots[0].first, knots[0].second + 1)
        "D" -> knots[0] = Pair(knots[0].first, knots[0].second - 1)
    }

    for (i in 1 until knots.size) {
        if (areTouching(knots[i - 1], knots[i])) {
            continue
        }

        if (knots[i - 1].first > knots[i].first && knots[i - 1].first - knots[i].first >= 1)
            knots[i] = Pair(knots[i].first + 1, knots[i].second)
        if (knots[i - 1].first < knots[i].first && knots[i].first - knots[i - 1].first >= 1)
            knots[i] = Pair(knots[i].first - 1, knots[i].second)
        if (knots[i - 1].second > knots[i].second && knots[i - 1].second - knots[i].second >= 1)
            knots[i] = Pair(knots[i].first, knots[i].second + 1)
        if (knots[i - 1].second < knots[i].second && knots[i].second - knots[i - 1].second >= 1)
            knots[i] = Pair(knots[i].first, knots[i].second - 1)
    }
}

fun areTouching(head: Pair<Int, Int>, tail: Pair<Int, Int>): Boolean {
    return abs(head.first - tail.first) <= 1 && abs(head.second - tail.second) <= 1
}