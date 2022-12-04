package day04

import readInput


fun main() {
    fun part1(input: List<String>): Int {
        var fullyEnclosedRanges = 0
        input.forEach { line ->
            val (rangeStringElf1, rangeStringElf2) = line.split(",")
            val (rangeStartElf1, rangeEndElf1) = rangeStringElf1.split("-").map { it.toInt() }
            val (rangeStartElf2, rangeEndElf2) = rangeStringElf2.split("-").map { it.toInt() }
            if (rangeStartElf1 in rangeStartElf2..rangeEndElf2 && rangeEndElf1 in rangeStartElf2..rangeEndElf2 || rangeStartElf2 in rangeStartElf1..rangeEndElf1 && rangeEndElf2 in rangeStartElf1..rangeEndElf1) {
                fullyEnclosedRanges++
            }
        }
        return fullyEnclosedRanges
    }

    fun part2(input: List<String>): Int {
        var partiallyEnclosedRanges = 0
        input.forEach { line ->
            val (rangeStringElf1, rangeStringElf2) = line.split(",")
            val (rangeStartElf1, rangeEndElf1) = rangeStringElf1.split("-").map { it.toInt() }
            val (rangeStartElf2, rangeEndElf2) = rangeStringElf2.split("-").map { it.toInt() }
            if (rangeStartElf1 in rangeStartElf2..rangeEndElf2 || rangeEndElf1 in rangeStartElf2..rangeEndElf2 || rangeStartElf2 in rangeStartElf1..rangeEndElf1 || rangeEndElf2 in rangeStartElf1..rangeEndElf1) {
                partiallyEnclosedRanges++
            }
        }
        return partiallyEnclosedRanges
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day04/Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("day04/day04")
    println(part1(input))
    println(part2(input))
}
