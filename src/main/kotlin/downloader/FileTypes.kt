package downloader

/**
 * List containing all currently supported filetypes
 */
val fileTypes = listOf(
    FileType("best", false),
    FileType("mp4", false),
    FileType("avi", false),
    FileType("m4a", false),

    FileType("mp3", true),
    FileType("wav", true),
    FileType("ogg", true),
    FileType("flac", true),
)

/**
 * Data class that stores relevant filetype information such as the [fileEnding] and whether
 * this is an [audioOnly] filetype
 * */
data class FileType(
    val fileEnding: String,
    val audioOnly: Boolean
)