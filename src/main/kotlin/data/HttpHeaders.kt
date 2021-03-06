package data


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class HttpHeaders(
    @JsonProperty("Accept")
    val accept: String?,
    @JsonProperty("Accept-Encoding")
    val acceptEncoding: String?,
    @JsonProperty("Accept-Language")
    val acceptLanguage: String?,
    @JsonProperty("Sec-Fetch-Mode")
    val secFetchMode: String?,
    @JsonProperty("User-Agent")
    val userAgent: String?
)