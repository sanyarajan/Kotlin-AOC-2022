@file:Suppress("SpellCheckingInspection")

package day06

import readInputText


fun main() {


    fun findDistinctSequenceOfLength(input: String, sequenceLength:Int): Int {

        (0..input.length - sequenceLength).forEach { i ->
            val sub = input.subSequence(i, i + sequenceLength)
            val set = sub.toSet()
            if (set.size == sequenceLength) {

                return i + sequenceLength

            }
        }
        return 0
    }

    fun part1(input: String): Int {
        // find the first set of 4 characters that are different
        return findDistinctSequenceOfLength(input, 4)
    }


    fun part2(input: String): Int {

        return findDistinctSequenceOfLength(input, 14)
    }

    // test if implementation meets criteria from the description, like:

    check(part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 7)
    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)
    check(part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 19)
    check(part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)
//    check(part2(testInput) == "MCD")

    val input = readInputText("day06/day06")
    println(part1(input))
    println(part2(input))
}
