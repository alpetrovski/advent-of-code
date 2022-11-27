package day05

import java.nio.file.Paths

fun main() {
    println(occupiedSeats().maxOrNull())
}

private fun occupiedSeats(): List<Int> {
    return Paths.get("src/day05/input.in").toFile().readLines().map { boardingPass ->
        val row = boardingPass.substring(0, 7)
                .replace("B", "1")
                .replace("F", "0")
                .let { binaryString -> Integer.parseInt(binaryString, 2)}
        val column = boardingPass.substring(7)
                .replace("R", "1")
                .replace("L", "0")
                .let { binaryString -> Integer.parseInt(binaryString, 2)}
        row * 8 + column
    }
}