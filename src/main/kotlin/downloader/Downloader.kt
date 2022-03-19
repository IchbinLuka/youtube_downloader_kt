package downloader

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import data.VideoInfo
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader

class YtDownloader(
    private val ytExePath: String
) {

    fun getVideoInfo(url: String): VideoInfo? {
        val dir = File(ytExePath).parentFile
        val processBuilder = ProcessBuilder(ytExePath, url, "-P ${dir.absolutePath}", "--write-info-json", "--no-download")
        val process = processBuilder.start()
        process.waitFor()
        val mapper = jacksonObjectMapper()

        val files = dir.listFiles()
        return if (files != null) {
            try {
                val infoFile = files.first { it.name.endsWith(".info.json") }
                mapper.readValue(String(infoFile.readBytes()), VideoInfo::class.java)
            } catch (e: NoSuchElementException) {
                println("No info json found")
                null
            }
        } else {
            null
        }
    }

    fun downloadVideo(url: String, destination: String, onProgress: (Double) -> Unit) {
        val processBuilder = ProcessBuilder(ytExePath, url, "-P $destination")
        val process = processBuilder.start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        while (true) {
            val line = reader.readLine() ?: break
            val percentLocation = line.indexOfFirst { it == '%' }
            if (line.contains("[download]") && percentLocation != -1) {
                println("Downloading")
                val progressString = line.substring(10, percentLocation)
                val progress = progressString.toDoubleOrNull()
                if (progress != null) {
                    onProgress(progress)
                }
            }
        }
    }

    companion object {
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
            outStream.close()
            return ytDlExe.absolutePath
        }

    }
}

data class YtDownloaderProcess(
    var onProgress: (Double) -> Unit,
    var isFinished: Boolean,
    val pid: Long
)