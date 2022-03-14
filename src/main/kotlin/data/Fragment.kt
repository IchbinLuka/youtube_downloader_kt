package data


import com.fasterxml.jackson.annotation.JsonProperty

data class Fragment(
    @JsonProperty("duration")
    val duration: Double?,
    @JsonProperty("path")
    val path: String?
)