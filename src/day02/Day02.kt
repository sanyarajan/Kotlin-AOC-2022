package day02

import readInput

enum class RPSOptions {
    ROCK, PAPER, SCISSORS;

    companion object {
        fun beatenBy(rpsOptions: RPSOptions): RPSOptions {
            return when (rpsOptions) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                SCISSORS -> ROCK
            }
        }
    }
}

enum class CodeOptions {
    A, B, C, X, Y, Z;


    companion object {
        fun valueToRPS(option: CodeOptions): RPSOptions {
            return when (option) {
                A -> RPSOptions.ROCK
                X -> RPSOptions.ROCK
                B -> RPSOptions.PAPER
                Y -> RPSOptions.PAPER
                C -> RPSOptions.SCISSORS
                Z -> RPSOptions.SCISSORS
            }
        }
    }
}

enum class DesiredOutcome {
    WIN, LOSE, DRAW;

    companion object {
        fun getDesiredOutcome(s: String): DesiredOutcome {
            return when (s) {
                "Z" -> WIN
                "X" -> LOSE
                "Y" -> DRAW
                else -> throw IllegalArgumentException("Invalid option")
            }
        }
    }
}

class RPSValues {
    companion object {
        private var rock = 1
        private var paper = 2
        private var scissors = 3
        fun getValue(playerOption: RPSOptions): Int {
            return when (playerOption) {
                RPSOptions.ROCK -> rock
                RPSOptions.PAPER -> paper
                RPSOptions.SCISSORS -> scissors
            }
        }
    }
}

fun main() {

    val inputLineRegex = """([ABC]) ([XYZ])""".toRegex()
    fun part1(input: List<String>): Int {
        var score = 0
        input.forEach { line ->
            val (opponent, player) = inputLineRegex.matchEntire(line)!!.destructured
            val opponentOption = CodeOptions.valueToRPS(CodeOptions.valueOf(opponent))
            val playerOption = CodeOptions.valueToRPS(CodeOptions.valueOf(player))
            score += when {
                opponentOption == playerOption -> {
                    3 + RPSValues.getValue(playerOption)
                }

                (opponentOption == RPSOptions.ROCK && playerOption == RPSOptions.SCISSORS) || (opponentOption == RPSOptions.PAPER && playerOption == RPSOptions.ROCK) || (opponentOption == RPSOptions.SCISSORS && playerOption == RPSOptions.PAPER) -> {
                    RPSValues.getValue(playerOption)
                }

                else -> {
                    6 + RPSValues.getValue(playerOption)
                }
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEach { line ->
            val (opponent, outcome) = inputLineRegex.matchEntire(line)!!.destructured
            val opponentOption = CodeOptions.valueToRPS(CodeOptions.valueOf(opponent))
            //convert outcome to DesiredOutcome
            val desiredOutcome = DesiredOutcome.getDesiredOutcome(outcome)
            score += when (desiredOutcome) {
                DesiredOutcome.DRAW -> {
                    3 + RPSValues.getValue(opponentOption)
                }

                DesiredOutcome.WIN -> {
                    6 + RPSValues.getValue(RPSOptions.beatenBy(opponentOption))
                }

                DesiredOutcome.LOSE -> {
                    RPSValues.getValue(RPSOptions.beatenBy(RPSOptions.beatenBy(opponentOption)))
                }

            }
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day02/day02")
    println(part1(input))
    println(part2(input))
}
