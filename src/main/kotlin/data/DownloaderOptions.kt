package data


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class DownloaderOptions(
    @JsonProperty("http_chunk_size")
    val httpChunkSize: Int?
)