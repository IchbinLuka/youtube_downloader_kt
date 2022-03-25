package util

import java.io.File
import java.io.FileOutputStream

fun findFile(parent: File, condition: (File) -> Boolean): File? {
    if (!parent.isDirectory) {
        return null
    }
    val children = parent.listFiles()
    return children?.firstOrNull(condition)
}

fun findJson(parent: File): File? {
    return findFile(parent = parent) {
        it.name.endsWith(".json")
    }
}

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