package day01

import readInput

fun main() {
    fun part1(input: List<String>): Pair<Int,Int> {
        // get a list of sums of ranges in the list separated by blanks
        val list = ArrayList<Int>()
        var total=0
        input
            .asSequence()
            .map { it.toIntOrNull() }
            .forEach {
                when {
                    it != null -> {
                        total+= it
                    }
                    else -> {
                        list.add(total)
                        total=0
                    }
                }
            }
        var currentTotal = 0
        var maxTotal = 0
        var currentElf = 1
        var maxElf = 1
        input.forEach { line ->
            //if the line is an integer
            val value = line.toIntOrNull()
            if ( value != null) {
                currentTotal += value
            }
            else
            {
                if(currentTotal > maxTotal)
                {
                    maxTotal = currentTotal
                    maxElf = currentElf
                }
                currentElf++
                currentTotal = 0
            }

        }
        return Pair(maxTotal, maxElf)
    }

    fun part2(input: List<String>): Int {
        var currentTotal = 0
        var currentElf = 1
        val totals = ArrayList<Int>()
        input.forEach { line ->
            //if the line is an integer
            val value = line.toIntOrNull()
            if ( value != null) {
                currentTotal += value
            }
            else
            {
               totals.add(currentTotal)
                currentElf++
                currentTotal = 0
            }
        }

        //sort the list and take the top 3
        totals.sortDescending()
        return totals.take(3).sum()


    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01/input")
//    check(part1(testInput) == 1)

    val input = readInput("day01/input")
    println(part1(input))
    println(part2(input))
}
