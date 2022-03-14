package data


import com.fasterxml.jackson.annotation.JsonProperty

data class Format(
    @JsonProperty("abr")
    val abr: Double?,
    @JsonProperty("acodec")
    val acodec: String?,
    @JsonProperty("asr")
    val asr: Int?,
    @JsonProperty("audio_ext")
    val audioExt: String?,
    @JsonProperty("container")
    val container: String?,
    @JsonProperty("downloader_options")
    val downloaderOptions: DownloaderOptions?,
    @JsonProperty("dynamic_range")
    val dynamicRange: String?,
    @JsonProperty("ext")
    val ext: String?,
    @JsonProperty("filesize")
    val filesize: Int?,
    @JsonProperty("filesize_approx")
    val filesizeApprox: Double?,
    @JsonProperty("format")
    val format: String?,
    @JsonProperty("format_id")
    val formatId: String?,
    @JsonProperty("format_note")
    val formatNote: String?,
    @JsonProperty("fps")
    val fps: Int?,
    @JsonProperty("fragments")
    val fragments: List<Fragment>?,
    @JsonProperty("height")
    val height: Int?,
    @JsonProperty("http_headers")
    val httpHeaders: HttpHeaders?,
    @JsonProperty("language")
    val language: String?,
    @JsonProperty("language_preference")
    val languagePreference: Int?,
    @JsonProperty("protocol")
    val protocol: String?,
    @JsonProperty("quality")
    val quality: Int?,
    @JsonProperty("resolution")
    val resolution: String?,
    @JsonProperty("source_preference")
    val sourcePreference: Int?,
    @JsonProperty("tbr")
    val tbr: Double?,
    @JsonProperty("url")
    val url: String?,
    @JsonProperty("vbr")
    val vbr: Double?,
    @JsonProperty("vcodec")
    val vcodec: String?,
    @JsonProperty("video_ext")
    val videoExt: String?,
    @JsonProperty("width")
    val width: Int?
)