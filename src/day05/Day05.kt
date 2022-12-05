package day05

import readInput
import kotlin.math.ceil


fun main() {
    val instructionsRegex = Regex("move ([0-9]+) from ([0-9]+) to ([0-9]+)")

    fun parseInput(input: List<String>): Pair<List<ArrayDeque<Char>>, List<String>> {
        val numStacks = ceil(input[0].length / 4.0).toInt()

        // list of length numStacks of stacks of chars
        val stacks = List(numStacks) { ArrayDeque<Char>() }
        var stackLines = 0
        run loop@{
            input.forEachIndexed { index, line ->
                if (line[1] == '1') {
                    stackLines = index
                    return@loop
                }
                for (i in 1 until numStacks * 4 - 1 step 4) {
                    if (line[i] != ' ')
                        stacks[(i - 1) / 4].addFirst(line[i])
                }
            }
        }

        val instructions = input.subList(stackLines + 2, input.size)
        return Pair(stacks, instructions)
    }

    fun part1(input: List<String>): String {
        var topCrates = ""
        val (stacks, instructions) = parseInput(input)

        instructions.forEach { instruction ->
            val (numCrates, fromStack, toStack) = instructionsRegex.find(instruction)!!.destructured
            val numCratesInt = numCrates.toInt()
            val fromStackInt = fromStack.toInt()
            val toStackInt = toStack.toInt()
            //move crates from fromStack to toStack
            for (i in 0 until numCratesInt) {
                stacks[toStackInt-1].addLast(stacks[fromStackInt-1].removeLast())
            }
        }

       // get the last crate from each stack
        stacks.forEach { stack ->
            topCrates += stack.last()
        }

        return topCrates
    }

    fun part2(input: List<String>): String {
        var topCrates = ""
        val (stacks, instructions) = parseInput(input)

        instructions.forEach { instruction ->
            val (numCrates, fromStack, toStack) = instructionsRegex.find(instruction)!!.destructured
            val numCratesInt = numCrates.toInt()
            val fromStackInt = fromStack.toInt()
            val toStackInt = toStack.toInt()
            //move crates from fromStack to toStack preserving the order of the crates
            val cratesToMove = ArrayDeque<Char>()
            for (i in 0 until numCratesInt) {
                cratesToMove.addLast(stacks[fromStackInt-1].removeLast())
            }
            for (i in 0 until numCratesInt) {
                stacks[toStackInt-1].addLast(cratesToMove.removeLast())
            }

        }

        // get the last crate from each stack
        stacks.forEach { stack ->
            topCrates += stack.last()
        }

        return topCrates
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day05/Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("day05/day05")
    println(part1(input))
    println(part2(input))
}
