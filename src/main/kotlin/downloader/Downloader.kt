package downloader

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import data.VideoInfo
import util.findJson
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class YtDownloader(
    private val ytExePath: String,
    private val ffmpegPath: String? = null
) {

    private val jsonMapper = jacksonObjectMapper()

    fun getVideoInfo(url: String): VideoInfo? {
        val dir = File(ytExePath).parentFile
        val processBuilder = ProcessBuilder(ytExePath, url, "-P ${dir.absolutePath}", "--write-info-json", "--no-download")
        val process = processBuilder.start()
        process.waitFor()

        val files = dir.listFiles()
        return if (files != null) {
            try {
                val infoFile = files.first { it.name.endsWith(".info.json") }
                val content = String(infoFile.readBytes())
                infoFile.delete()
                jsonMapper.readValue(content, VideoInfo::class.java)

            } catch (e: NoSuchElementException) {
                println(e)
                println("No info json found")
                null
            }
        } else {
            null
        }
    }

    fun downloadVideo(
        url: String,
        destination: String,
        onProgress: (Double) -> Unit,
        onVideoInfo: ((VideoInfo) -> Unit)? = null,
        outputType: FileType = fileTypes[0]
    ) {
        val runtime = Runtime.getRuntime()
        //val command = mutableListOf<String>(ytExePath, url)
        val builder = StringBuilder(ytExePath)
        val ffmpegInPATH = try {
            runtime.exec("ffmpeg -version")  // Check if ffmpeg is installed
            true
        } catch (_: IOException) {
            false
        }
        builder.apply {
            append(" -P $destination")
            append(" --write-info-json")
            if (ffmpegPath != null || ffmpegInPATH) {
                if (ffmpegPath != null) {
                    append(" --ffmpeg-location $ffmpegPath")
                }
                if (outputType.audioOnly) {
                    append(" --extract-audio") // Extract audio
                    append(" --audio-format ${outputType.fileEnding}")
                } else {
                    append(" --format ${outputType.fileEnding}")
                }
            }
            if (url.startsWith("https://")) {
                append(" $url")
            } else {
                append(" ytsearch:\"${url}\" --max-downloads 1")
            }
        }
        println(builder.toString()) //TODO: Remove println
        val process = runtime.exec(builder.toString())
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var metadataPrepared = false
        while (true) {
            val line = reader.readLine() ?: break
            println(line)
            if (metadataPrepared) {
                val json = findJson(File(destination))
                json?.let {
                    val content = String(it.readBytes())
                    it.delete()
                    if (onVideoInfo != null) {
                        try {
                            onVideoInfo(
                                jsonMapper.readValue(content, VideoInfo::class.java)
                            )
                        } catch (_: Exception) {
                            println("Could not parse json file")
                        }
                    }
                }
            }
            if (line.startsWith("[info] Writing video metadata")) {
                metadataPrepared = true
            }
            val percentLocation = line.indexOfFirst { it == '%' }
            println(line)
            if (line.contains("[download]") && percentLocation != -1) {
                val progressString = line.substring(10, percentLocation)
                val progress = progressString.toDoubleOrNull()
                if (progress != null) {
                    onProgress(progress)
                }
            }
        }
        println(process.waitFor())
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
                return ytDlExe.absolutePath
            }
            ytDlExe.createNewFile()
            val outStream = FileOutputStream(ytDlExe)
            outStream.write(stream!!.readAllBytes())
            outStream.close()
            return ytDlExe.absolutePath
        }

    }
}