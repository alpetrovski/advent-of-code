package day10

import java.nio.file.Paths

fun main() {
    val orderedJoltages = mutableListOf(0).apply { // start with 0
        this.addAll(Paths.get("src/day10/input.in").toFile().readLines().map { it.toInt() }.sorted())
        this.add(this.maxOrNull()!! + 3)
    }

    val state = mutableMapOf(0 to 1L)
    for (index in 1 until orderedJoltages.size) {
        state[index] = IntRange(index - 3, index - 1)
            .filter { prevIndex -> prevIndex >= 0 && orderedJoltages[index] - orderedJoltages[prevIndex] <= 3 }
            .map { prevIndex -> state[prevIndex]!! }.sum()
    }

    println(state[orderedJoltages.size - 1])
}