package com.aleksandarpetrovski.aoc2020.day02

import java.nio.file.Paths

fun main() {
    val correctPasswords = Paths.get("2020-kotlin/src/day02/input.in").toFile().readLines().map {
        val parts = it.split("-"," ", ":")

        val minOccurences = parts[0].toInt()
        val maxOccurences = parts[1].toInt()
        val letter = parts[2].toCharArray()[0]
        val password = parts[4]

        val occurences = password.count { passwordChar -> passwordChar == letter }
        occurences in minOccurences..maxOccurences
    }.filter { it }.count()

    println(correctPasswords)

}
