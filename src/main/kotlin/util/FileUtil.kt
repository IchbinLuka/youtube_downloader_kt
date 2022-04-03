package util

import java.io.File
import java.io.FileOutputStream

/**
 * Searches for files in the [parent] directory until one is found
 * that meets the [condition]
 */
fun findFile(parent: File, condition: (File) -> Boolean): File? {
    if (!parent.isDirectory) {
        return null
    }
    val children = parent.listFiles()
    return children?.firstOrNull(condition)
}

/**
 * Returns the first .json file in the [parent] directory that is found and null
 * if there are no .json files
 */
fun findJson(parent: File): File? {
    return findFile(parent = parent) {
        it.name.endsWith(".json")
    }
}

/**
 * Extracts a file with a [path] from the packaged jar file to a [destination]
 */
fun extractFile(path: String, destination: String) {
    val mainClass = Class.forName("MainKt")
    val stream = mainClass.getResourceAsStream(path)

    val file = File(destination)
    file.parentFile.mkdirs()

    if (!file.exists()) {
        file.createNewFile()
    }
    val outStream = FileOutputStream(file)
    outStream.write(stream!!.readAllBytes())
    outStream.close()
}