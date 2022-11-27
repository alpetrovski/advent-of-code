package day07

import java.nio.file.Paths


fun main() {

    val colorMap = HashMap<String, MutableSet<String>>()

    val colorRegex = Regex("(.*) bags contain .*")
    val containingBagsRegex = Regex("[0-9]+ ([a-z]+ [a-z]+) bag[s]*[\\.,]{1}")

    Paths.get("src/day07/input.in").toFile().readLines()
        .filter { it.contains("no other bags.").not() }
        .forEach { line ->
            val parentColor = colorRegex.find(line)!!.groupValues[1]

            containingBagsRegex.findAll(line).forEach {
                val color = it.groupValues[1]

                if (!colorMap.containsKey(color)) {
                    colorMap[color] = mutableSetOf(parentColor)
                }

                colorMap[color]!!.add(parentColor)
            }

        }

    val result = HashSet<String>()
    var parentColors = calculateParentColors(colorMap, setOf("shiny gold"))
    while (!result.containsAll(parentColors)) {
        result.addAll(parentColors)
        parentColors = calculateParentColors(colorMap, parentColors)
    }
    println(result.count())
}

fun calculateParentColors(colorMap: Map<String, Set<String>>, colors: Set<String>): Set<String> {
    val result = HashSet<String>()
    colors.forEach {
        if (colorMap.contains(it)) {
            result.addAll(colorMap[it]!!)
        }
    }
    return result
}