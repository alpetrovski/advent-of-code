package day02

import java.nio.file.Paths

fun main() {
    val correctPasswords = Paths.get("src/day02/input.in").toFile().readLines().map {
        val parts = it.split("-"," ", ":")

        val position1 = parts[0].toInt() - 1
        val position2 = parts[1].toInt() - 1
        val letter = parts[2].toCharArray()[0]
        val password = parts[4]

        (password[position1] == letter).xor(password[position2] == letter)
    }.filter { it }.count()

    println(correctPasswords)

}