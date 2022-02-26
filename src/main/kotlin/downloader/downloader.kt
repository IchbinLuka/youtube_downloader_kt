package downloader

import java.io.File
import java.io.FileOutputStream

fun prepareYtDlExe(): String {
    val mainClass = Class.forName("MainKt")
    val stream = mainClass.getResourceAsStream("yt-dlp.exe")

    val tmpdir: String = System.getProperty("java.io.tmpdir")
    println(tmpdir)
    val dir = File("$tmpdir/yt_downloader")
    if (!dir.exists()) {
        dir.mkdir()
    }
    val ytDlExe = File("${dir.absolutePath}/yt-dlp.exe")
    if (ytDlExe.exists()) {
        ytDlExe.delete()
    }
    ytDlExe.createNewFile()
    val outStream = FileOutputStream(ytDlExe)
    outStream.write(stream!!.readAllBytes())
    return ytDlExe.absolutePath
}