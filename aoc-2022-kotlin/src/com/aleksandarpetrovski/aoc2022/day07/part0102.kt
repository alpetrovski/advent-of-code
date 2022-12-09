package com.aleksandarpetrovski.aoc2022.day07

import com.aleksandarpetrovski.aoc2022.readInputLines

private data class AocFile(val name: String, val size: Long)
private data class AocDir(
    val name: String,
    val parent: AocDir? = null,
    val files: MutableList<AocFile> = mutableListOf(),
    val dirs: MutableList<AocDir> = mutableListOf()
) {
    fun size(): Long = files.sumOf { it.size } + dirs.sumOf { it.size() }
}

private const val TOTAL_DISK_SPACE = 70000000L
private const val FREE_DISK_SPACE_NEEDED = 30000000

fun main() {
    val rootDir = createFileSystem(readInputLines(day = 7))
    val directorySizes = getListOfDirectorySizes(rootDir)

    val resultPart1 = directorySizes.filter { it <= 100000 }.sum()

    val requiredAdditionalSpace = FREE_DISK_SPACE_NEEDED - (TOTAL_DISK_SPACE - rootDir.size())
    val resultPart2 = directorySizes.sorted().first { it >= requiredAdditionalSpace }

    println("Result Part 01: $resultPart1")
    println("Result Part 02: $resultPart2")
}

private fun getListOfDirectorySizes(dir: AocDir): List<Long> = mutableListOf(dir.size()).apply {
    dir.dirs.forEach { this.addAll(getListOfDirectorySizes(it)) }
}

private fun createFileSystem(inputs: List<String>): AocDir {
    var current = AocDir("/")

    inputs.subList(1, inputs.size).forEach { line ->
        if (line.startsWith("dir")) {
            val (_, dirName) = line.split(" ")
            current.dirs.add(AocDir(dirName, parent = current))
        } else if (line.first().isDigit()) {
            val (size, name) = line.split(" ")
            current.files.add(AocFile(name, size.toLong()))
        } else if (line == "\$ cd ..") {
            current = current.parent!!
        } else if (line.startsWith("\$ cd ")) {
            val (_, _, dirName) = line.split(" ")
            current = current.dirs.first { it.name == dirName }
        }
    }

    while (current.parent != null) {
        current = current.parent!!
    }

    return current
}
