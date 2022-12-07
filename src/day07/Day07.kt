package day07

import readInput


// class to represent an ElfFile that has a name and size
class ElfFile(private val name: String, val size: Int) {
    override fun toString(): String {
        return "$name (file, size=$size)"
    }
}

// class to represent a folder that has a name and a list of ElfFiles and Folders
class Directory(val name: String, val parent: Directory? = null) {
    val files = mutableListOf<ElfFile>()
    val folders = mutableListOf<Directory>()

    override fun toString(): String {
        return "$name (dir, size=${getSize()})"
    }

    fun getSize(): Int {
        var size = 0
        files.forEach { size += it.size }
        folders.forEach { size += it.getSize() }
        return size
    }

    @Suppress("unused", "MemberVisibilityCanBePrivate")
    fun printTree(indent: String = "") {
        println("$indent- $this")
        files.forEach { println("$indent - $it") }
        folders.forEach { it.printTree("$indent ") }
    }
}

fun main() {

    fun sumSizesOfAllFoldersAndSubFolders(folder: Directory, maxSize: Int): Int {
        var size = 0
        folder.folders.forEach { size += sumSizesOfAllFoldersAndSubFolders(it, maxSize) }
        if (folder.getSize() < maxSize) {
            size += folder.getSize()
        }
        return size
    }

    fun getListOfDirectoriesBigEnough(folder: Directory, maxSize: Int): List<Directory> {
        val list = mutableListOf<Directory>()
        folder.folders.forEach { list.addAll(getListOfDirectoriesBigEnough(it, maxSize)) }
        if (folder.getSize() >= maxSize) {
            list.add(folder)
        }
        return list
    }

    @Suppress("ControlFlowWithEmptyBody")
    fun getRootOfDirectoryTree(input: List<String>): Directory {
        val root = Directory("/")
        var currentDir = root

        input.forEach { line ->
            if (line == "$ cd /") {
            } else if (!line.startsWith("$")) {
                // file listing
                val parts = line.split(" ")
                if (parts[0] == "dir") {
                    // directory listing
                    val dir = Directory(parts[1], currentDir)
                    currentDir.folders.add(dir)
                } else {
                    // file listing
                    val file = ElfFile(parts[1], parts[0].toInt())
                    currentDir.files.add(file)
                }
            } else if (line == "$ cd ..") {
                currentDir = currentDir.parent!!
            } else if (line.startsWith("$ cd ")) {
                val dirName = line.substring(5)
                currentDir = currentDir.folders.first { it.name == dirName }
            }
        }
        return root
    }

    fun part1(input: List<String>): Int {
        val root = getRootOfDirectoryTree(input)
        // root.printTree()

        return sumSizesOfAllFoldersAndSubFolders(root, 100000)
    }

    fun part2(input: List<String>): Int {
        val totalSpace = 70000000
        val desiredSpace = 30000000
        val root = getRootOfDirectoryTree(input)

        val unusedSpace = totalSpace - root.getSize()
        val spaceToFree = desiredSpace - unusedSpace

        return getListOfDirectoriesBigEnough(root, spaceToFree).minByOrNull { it.getSize() }!!.getSize()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day07/Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("day07/day07")
    println(part1(input))
    println(part2(input))
}
