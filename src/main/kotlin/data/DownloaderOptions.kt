package data


import com.fasterxml.jackson.annotation.JsonProperty

data class DownloaderOptions(
    @JsonProperty("http_chunk_size")
    val httpChunkSize: Int?
)