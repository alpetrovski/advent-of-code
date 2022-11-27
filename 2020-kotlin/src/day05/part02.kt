package day05

import java.nio.file.Paths

fun main() {
    println("Seat: ${findMissingSeat(occupiedSeats())}")
}

private fun findMissingSeat(occupiedSeats: Set<Int>): Int {
    val firstSeat = occupiedSeats.minOrNull()!!
    val lastSeat = firstSeat + occupiedSeats.size + 1

    return IntRange(firstSeat, lastSeat).first {
        seat -> occupiedSeats.contains(seat).not()
    }
}

private fun occupiedSeats(): Set<Int> {
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
    }.toSet()
}