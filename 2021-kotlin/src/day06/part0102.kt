package day06

import java.nio.file.Paths

fun main() {
    var fishListMap = mutableMapOf<Int, Long>(0 to 0, 1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0)

    Paths.get("src/day06/input.in").toFile().readLines()[0].split(",").map { it.toInt() }.forEach {
        fishListMap[it] = fishListMap.getOrDefault(it, 0) + 1
    }

    repeat(80) { executeCycle(fishListMap) }
    println("RESULT part 01: ${countActiveFish(fishListMap)}")

    repeat(176) { executeCycle(fishListMap) }
    println("RESULT part 02: ${countActiveFish(fishListMap)}")
}

fun executeCycle(fishListMap: MutableMap<Int, Long>) {
    (-1..7).forEach {
        fishListMap[it] = fishListMap[it + 1]!!
    }
    fishListMap[6] = fishListMap[6]!! + fishListMap[-1]!!
    fishListMap[8] = fishListMap[-1]!!
}

fun countActiveFish(fishListMap: MutableMap<Int, Long>) =
    fishListMap.filter { it.key > -1 }.map { it.value }.sum()