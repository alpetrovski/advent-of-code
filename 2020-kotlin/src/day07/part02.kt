package day07

import java.nio.file.Paths

fun main() {

    val bagConfigMap = HashMap<String, MutableMap<String, Int>>()

    val colorRegex = Regex("(.*) bags contain .*")
    val containingBagsRegex = Regex("([0-9]+) ([a-z]+ [a-z]+) bag[s]*[\\.,]{1}")

    Paths.get("src/day07/input.in").toFile().readLines()
        .filter { it.contains("no other bags.").not() }
        .forEach { line ->
            val parentColor = colorRegex.find(line)!!.groupValues[1]

            bagConfigMap[parentColor] = HashMap()
            containingBagsRegex.findAll(line).forEach {
                val color = it.groupValues[2]
                val count = it.groupValues[1].toInt()
                bagConfigMap[parentColor]!![color] = count
            }
        }

    println(countInnerBags(bagConfigMap, "shiny gold"))
}

private fun countInnerBags(bagConfigMap: Map<String, Map<String, Int>>, color: String): Int {
    if (!bagConfigMap.containsKey(color)) {
        return 0
    }

    return bagConfigMap[color]!!.map {
        it.value * (1 + countInnerBags(bagConfigMap, it.key))
    }.sum()

}