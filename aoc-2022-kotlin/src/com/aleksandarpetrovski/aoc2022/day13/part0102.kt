package com.aleksandarpetrovski.aoc2022.day13

import com.aleksandarpetrovski.aoc2022.readInputLines

data class Packet(val raw: String, val data: List<Any>)

private val dividerPackets = listOf("[[2]]", "[[6]]")
private val packetComparator = Comparator<Packet> { packet1, packet2 -> comparePackets(packet1.data, packet2.data) }

fun main() {
    val packets = readInputLines(day = 13, isTest = false)
        .filter { it.isNotEmpty() }
        .map { Packet(it, parsePacket(it)) }

    val resultPart1 = packets.chunked(2).mapIndexed { index, packetsPair ->
        if (packetComparator.compare(packetsPair[0], packetsPair[1]) < 0) index + 1 else -1
    }.filter { it != -1 }.sum()

    println("Result Part 1: $resultPart1")

    val packetsWithDividers = packets.toMutableList().apply {
        dividerPackets.forEach { this.add(Packet(it, parsePacket(it))) }
    }

    val resultPart2 = packetsWithDividers.sortedWith(packetComparator)
        .mapIndexed { index, packet -> if (dividerPackets.contains(packet.raw)) index + 1 else -1 }
        .filter { it != -1 }.reduce { accumulator, element -> accumulator * element }

    println("Result Part 2: $resultPart2")
}

private fun parsePacket(packetChars: String): List<Any> {
    val previousLists = ArrayDeque<MutableList<Any>>()
    var currentList = mutableListOf<Any>()
    var currentBuffer = ""

    for (packetChar in packetChars) {
        when (packetChar) {
            '[' -> {
                previousLists.add(currentList)
                currentList = mutableListOf()
            }

            ']' -> {
                if (currentBuffer != "") {
                    currentList.add(currentBuffer.toInt())
                    currentBuffer = ""
                }
                val childList = currentList
                currentList = previousLists.removeLast()
                currentList.add(childList)
            }

            ',' -> {
                if (currentBuffer != "") {
                    currentList.add(currentBuffer.toInt())
                    currentBuffer = ""
                }
            }

            else -> {
                currentBuffer += packetChar
            }
        }
    }

    if (currentBuffer != "") {
        currentList.add(currentBuffer.toInt())
    }
    return currentList
}

private fun comparePackets(leftValue: List<*>, rightValue: List<*>): Int {
    for (i in leftValue.indices) {

        // if the right list is out of elements
        if (!rightValue.indices.contains(i)) {
            return 1
        }
        val leftInput = leftValue[i]
        val rightInput = rightValue[i]

        if (leftInput is Int && rightInput is Int) {
            // both values are integers
            val result = leftInput - rightInput
            if (result != 0) {
                return result
            }
        } else if (leftInput is List<*> && rightInput is List<*>) {
            // both values are lists
            val result = comparePackets(leftInput, rightInput)
            if (result != 0) {
                return result
            }
        } else {
            // one of the values is integer, the other one is list
            val leftInputAsList = if (leftInput is List<*>) leftInput else listOf(leftInput)
            val rightInputAsList = if (rightInput is List<*>) rightInput else listOf(rightInput)

            val result = comparePackets(leftInputAsList, rightInputAsList)
            if (result != 0) {
                return result
            }
        }
    }

    // if the left list runs out of elements, consider in order
    if (rightValue.size > leftValue.size) {
        return -1
    }

    return 0
}