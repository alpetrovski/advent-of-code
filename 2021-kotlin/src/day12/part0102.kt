package day12

import java.nio.file.Paths

fun main() {
    val caveConnections = mutableMapOf<String, MutableSet<String>>()
    Paths.get("src/day12/input.in").toFile().readLines().forEach { line ->
        val cave1 = line.split("-")[0]
        val cave2 = line.split("-")[1]
        caveConnections.getOrPut(cave1) { mutableSetOf() }.add(cave2)
        caveConnections.getOrPut(cave2) { mutableSetOf() }.add(cave1)
    }

    val resultPart1 = traverse(caveConnections, listOf("start"), 1).count()
    val resultPart2 = traverse(caveConnections, listOf("start"), 2).count()

    println("Result Part 1: $resultPart1")
    println("Result Part 2: $resultPart2")
}

fun traverse(
    caveConnections: Map<String, Set<String>>,
    path: List<String>,
    maxVisits: Int
): List<String> {
    val currentCave = path.last()
    if (currentCave == "end") {
        return listOf(path.joinToString(","))
    }

    return caveConnections[currentCave]!!
        .filter { caveName -> caveName != "start" }
        .filter { cave ->
            cave.isBigCave()
                    || path.notVisitedYet(cave)
                    || (path.noSmallCaveVisitedMoreThanOnce() && path.caveVisitsLessThan(cave, maxVisits))
        }
        .map { cave -> traverse(caveConnections, path + cave, maxVisits) }
        .flatMap { it.asIterable() }
}

fun String.isBigCave() = this == this.uppercase()

fun List<String>.noSmallCaveVisitedMoreThanOnce() =
    this.filter { caveName -> caveName == caveName.lowercase() }
        .groupBy { it }
        .any { it.value.size > 1 }
        .not()

fun List<String>.notVisitedYet(cave: String) = this.contains(cave).not()

fun List<String>.caveVisitsLessThan(cave: String, maxVisitsAllowed: Int) =
    this.count { it == cave } < maxVisitsAllowed