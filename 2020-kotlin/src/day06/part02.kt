package day06

import java.nio.file.Paths

fun main() {
    var result = 0

    Paths.get("src/day06/input.in").toFile().readLines().map { it.toSet() }.reduce { previous, current ->
        if (current.isEmpty()) {
            result += previous.size
            CharRange('a', 'z').toSet()
        } else {
            previous.intersect(current)
        }
    }.also {
        result += it.size
    }

    println(result)
}

