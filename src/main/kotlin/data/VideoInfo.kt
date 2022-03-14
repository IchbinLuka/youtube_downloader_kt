package data


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class VideoInfo(
    @JsonProperty("abr")
    val abr: Double?,
    @JsonProperty("acodec")
    val acodec: String?,
    @JsonProperty("age_limit")
    val ageLimit: Int?,
    @JsonProperty("asr")
    val asr: Int?,
    @JsonProperty("availability")
    val availability: String?,
    @JsonProperty("categories")
    val categories: List<String>?,
    @JsonProperty("channel")
    val channel: String,
    @JsonProperty("channel_follower_count")
    val channelFollowerCount: Int?,
    @JsonProperty("channel_id")
    val channelId: String,
    @JsonProperty("channel_url")
    val channelUrl: String?,
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("display_id")
    val displayId: String?,
    @JsonProperty("duration")
    val duration: Int,
    @JsonProperty("duration_string")
    val durationString: String?,
    @JsonProperty("dynamic_range")
    val dynamicRange: String?,
    @JsonProperty("epoch")
    val epoch: Int?,
    @JsonProperty("ext")
    val ext: String?,
    @JsonProperty("extractor")
    val extractor: String?,
    @JsonProperty("extractor_key")
    val extractorKey: String?,
    @JsonProperty("filesize_approx")
    val filesizeApprox: Int?,
    @JsonProperty("format")
    val format: String?,
    @JsonProperty("format_id")
    val formatId: String?,
    @JsonProperty("format_note")
    val formatNote: String?,
    @JsonProperty("formats")
    val formats: List<Format>?,
    @JsonProperty("fps")
    val fps: Int?,
    @JsonProperty("fulltitle")
    val fulltitle: String?,
    @JsonProperty("height")
    val height: Int?,
    @JsonProperty("id")
    val id: String,
    @JsonProperty("is_live")
    val isLive: Boolean?,
    @JsonProperty("like_count")
    val likeCount: Int?,
    @JsonProperty("live_status")
    val liveStatus: String?,
    @JsonProperty("playable_in_embed")
    val playableInEmbed: Boolean?,
    @JsonProperty("protocol")
    val protocol: String?,
    @JsonProperty("resolution")
    val resolution: String?,
    @JsonProperty("tags")
    val tags: List<String>?,
    @JsonProperty("tbr")
    val tbr: Double?,
    @JsonProperty("thumbnail")
    val thumbnail: String?,
    @JsonProperty("thumbnails")
    val thumbnails: List<Thumbnail>,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("_type")
    val type: String?,
    @JsonProperty("upload_date")
    val uploadDate: String?,
    @JsonProperty("uploader")
    val uploader: String?,
    @JsonProperty("uploader_id")
    val uploaderId: String?,
    @JsonProperty("uploader_url")
    val uploaderUrl: String?,
    @JsonProperty("vbr")
    val vbr: Double?,
    @JsonProperty("vcodec")
    val vcodec: String?,
    @JsonProperty("view_count")
    val viewCount: Int,
    @JsonProperty("was_live")
    val wasLive: Boolean?,
    @JsonProperty("webpage_url")
    val webpageUrl: String?,
    @JsonProperty("webpage_url_basename")
    val webpageUrlBasename: String?,
    @JsonProperty("webpage_url_domain")
    val webpageUrlDomain: String?,
    @JsonProperty("width")
    val width: Int?
)