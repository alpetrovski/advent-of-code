package day16

import java.math.BigInteger
import java.nio.file.Paths
import java.util.*

fun main() {
    val input = Paths.get("2021-kotlin/src/day16/input.in").toFile().readLines()[0].toCharArray()
        .map { Integer.parseInt(it.toString(), 16).toString(2).padStart(4, '0').toCharArray() }
        .flatMap { it.asIterable() }
        .let { LinkedList(it) }

    val topLevelPacket = readPacket(input)

    println("Result Part 1: ${topLevelPacket.versionSum()}")
    println("Result Part 2: ${topLevelPacket.calculateValue()}")
}

fun readPacket(input: Queue<Char>): Packet {
    var packetHeader = input.nextBits(6)

    val version = packetHeader.substring(0, 3).toInt(2)
    val type = packetHeader.substring(3, 6).toInt(2)

    return Packet(
        version = version,
        type = type,
        value = readValue(input, type),
        subpackets = readSubpackets(input, type)
    )
}

fun readValue(input: Queue<Char>, type: Int): BigInteger {
    if (type != 4) {
        return BigInteger.ZERO
    }

    var packetBinary = input.nextBits(5)
    var literalValue = packetBinary.substring(1)

    while (packetBinary.startsWith("1")) {
        packetBinary = input.nextBits(5)
        literalValue += packetBinary.substring(1)
    }

    return BigInteger(literalValue, 2)
}

fun readSubpackets(input: Queue<Char>, type: Int): List<Packet> {
    if (type == 4) {
        return emptyList()
    }

    val subpackets = mutableListOf<Packet>()
    val lenghtTypeId = input.nextBits()

    if (lenghtTypeId == "0") {
        val lenghtOfBitsForSubpackets = Integer.parseInt(input.nextBits(15), 2)
        val subpacketsInput = LinkedList((1..lenghtOfBitsForSubpackets).map { input.poll() })

        while (subpacketsInput.isNotEmpty()) {
            subpackets.add(readPacket(subpacketsInput))
        }
    } else {
        val numberOfSubpackets = Integer.parseInt(input.nextBits(11), 2)
        repeat(numberOfSubpackets) {
            subpackets.add(readPacket(input))
        }
    }
    return subpackets
}

fun Queue<Char>.nextBits(numberOfBits: Int = 1) =
    (1..numberOfBits).map { this.poll() }.joinToString("")

data class Packet(
    val version: Int,
    val type: Int,
    val value: BigInteger,
    val subpackets: List<Packet>
) {
    fun versionSum(): Int = version + subpackets.sumOf { it.versionSum() }

    fun calculateValue(): BigInteger {
        return when (type) {
            0 -> subpackets.sumOf { it.calculateValue() }
            1 -> subpackets.map { it.calculateValue() }.reduce { packet1, packet2 -> packet1.multiply(packet2) }
            2 -> subpackets.minOf { it.calculateValue() }
            3 -> subpackets.maxOf { it.calculateValue() }
            4 -> value
            5 -> if (subpackets[0].calculateValue() > subpackets[1].calculateValue()) BigInteger.ONE else BigInteger.ZERO
            6 -> if (subpackets[0].calculateValue() < subpackets[1].calculateValue()) BigInteger.ONE else BigInteger.ZERO
            7 -> if (subpackets[0].calculateValue() == subpackets[1].calculateValue()) BigInteger.ONE else BigInteger.ZERO
            else -> BigInteger.ZERO
        }
    }
}
