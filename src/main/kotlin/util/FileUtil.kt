package util

import java.io.File

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