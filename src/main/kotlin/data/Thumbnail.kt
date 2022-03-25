package data


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class Thumbnail(
    @JsonProperty("height")
    val height: Int?,
    @JsonProperty("id")
    val id: String?,
    @JsonProperty("preference")
    val preference: Int?,
    @JsonProperty("resolution")
    val resolution: String?,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("width")
    val width: Int?
)