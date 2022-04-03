package downloader

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import data.VideoInfo
import util.extractFile
import util.findJson
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

/**
 * Wrapper for yt-dlp executable which provides utilities for fetching video information
 * and downloading Videos
 */
class YtDownloader(
    private val ytExePath: String,
    private var ffmpegPath: String? = null
) {

    private val jsonMapper = jacksonObjectMapper()

    private val ffmpegInPATH = try {
        Runtime.getRuntime().exec("ffmpeg -version")  // Check if ffmpeg is installed
        true
    } catch (_: IOException) {
        false
    }

    init {
        if (!ffmpegInPATH && ffmpegPath == null) {
            ffmpegPath = prepareFfmpeg()
        }
    }



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

    /** Download Video with [url] to [destination] as [outputType] */
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
                        } catch (e: Exception) {
                            println("Could not parse json file")
                            println(e)
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
        /** Extracts the file with [name] from the jar to the systems temp directory */
        private fun prepareExe(name: String): String {
            val path = "${System.getProperty("java.io.tmpdir")}/yt_downloader/$name"
            val dir = File(path)
            if (dir.exists()) {
                return path
            }
            extractFile(name, path)
            return path
        }
        /** Prepares the yt-dlp executable by extracting it to the Temp dir */
        fun prepareYtDlExe(): String {
            return prepareExe("yt-dlp.exe")
        }
        /** Prepares the ffmpeg executable by extracting it to the Temp dir */
        fun prepareFfmpeg(): String {
            return prepareExe("ffmpeg.exe")
        }

    }
}