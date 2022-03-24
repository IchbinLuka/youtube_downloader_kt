package downloader

val fileTypes = listOf(
    FileType("mp4", false),
    FileType("avi", false),

    FileType("mp3", true),
    FileType("wav", true),
)

data class FileType(
    val fileEnding: String,
    val audioOnly: Boolean
)