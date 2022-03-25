package downloader

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

data class FileType(
    val fileEnding: String,
    val audioOnly: Boolean
)