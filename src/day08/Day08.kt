package day08

import readInputText

fun main() {
    fun getTreeGrid(input: String): MutableList<MutableList<Int>> {
        //a square grid of tree heights
        val treeGrid = mutableListOf<MutableList<Int>>()
        // convert input to treeGrid
        input.lines().forEach { line ->
            val row = mutableListOf<Int>()
            line.forEach { char ->
                row.add(char.toString().toInt())
            }
            treeGrid.add(row)
        }
        return treeGrid
    }

    fun part1(input: String): Int {
        var numVisible = 0
        val treeGrid = getTreeGrid(input)

        // count all trees on the edges
        numVisible += treeGrid[0].size * 4 - 4
        var visible: Boolean
        // for each tree not on the edges consider it to be visible if it is taller than any tree in the same row or column
        for (i in 1 until treeGrid.size - 1) {
            for (j in 1 until treeGrid[i].size - 1) {
                visible = false
                val treeHeight = treeGrid[i][j]
                // check row
                if (treeGrid[i].subList(0, j).all { it < treeHeight } || treeGrid[i].subList(j + 1, treeGrid[i].size)
                        .all { it < treeHeight }) {
                    visible = true
                }
                // check column
                if (treeGrid.subList(0, i).all { it[j] < treeHeight } || treeGrid.subList(i + 1, treeGrid.size)
                        .all { it[j] < treeHeight }) {
                    visible = true
                }
                if (visible) {
                    numVisible++
                }
            }
        }

        //println(numVisible)
        return numVisible
    }

    fun part2(input: String): Int {
        val treeGrid = getTreeGrid(input)
        var maxScenicScore = 0
        var numVisibleTop: Int
        var numVisibleBottom: Int
        var numVisibleLeft: Int
        var numVisibleRight: Int
        var scenicScore: Int
        // for each tree count the number of trees visible from that tree in each direction separately
        for (i in 0 until treeGrid.size) {
            for (j in 0 until treeGrid[i].size) {
                numVisibleTop = 0
                numVisibleBottom = 0
                numVisibleLeft = 0
                numVisibleRight = 0
                val treeHeight = treeGrid[i][j]
                // for this tree count how many are visible to the top stopping at the first tree taller or equal in height to this one
                for (k in i - 1 downTo 0) {
                    if (treeGrid[k][j] >= treeHeight) {
                        numVisibleTop++
                        break
                    }
                    numVisibleTop++
                }
                // for this tree count how many are visible to the bottom stopping at the first tree taller or equal in height to this one
                for (k in i + 1 until treeGrid.size) {
                    if (treeGrid[k][j] >= treeHeight) {
                        numVisibleBottom++
                        break
                    }
                    numVisibleBottom++
                }
                // for this tree count how many are visible to the left stopping at the first tree taller or equal in height to this one
                for (k in j - 1 downTo 0) {
                    if (treeGrid[i][k] >= treeHeight) {
                        numVisibleLeft++
                        break
                    }
                    numVisibleLeft++
                }
                // for this tree count how many are visible to the right stopping at the first tree taller or equal in height to this one
                for (k in j + 1 until treeGrid[i].size) {
                    if (treeGrid[i][k] >= treeHeight) {
                        numVisibleRight++
                        break
                    }
                    numVisibleRight++
                }

                //println("tree at $i, $j has $numVisible visible")
                // calculate the scenic score for this tree
                scenicScore = numVisibleTop * numVisibleBottom * numVisibleLeft * numVisibleRight
                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore
                }
            }
        }

        //println(maxScenicScore)

        return maxScenicScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputText("day08/Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInputText("day08/day08")
    println(part1(input))
    println(part2(input))
}
