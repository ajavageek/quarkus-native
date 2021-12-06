package ch.frankel.blog

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection
import java.net.URI

@RegisterForReflection
data class Model(
    @field:JsonProperty("code")
    var code: Int? = null,
    @field:JsonProperty("status")
    var status: String? = null,
    @field:JsonProperty("copyright")
    var copyright: String? = null,
    @field:JsonProperty("attributionText")
    var attributionText: String? = null,
    @field:JsonProperty("attributionHTML")
    var attributionHTML: String? = null,
    @field:JsonProperty("etag")
    var etag: String? = null,
    @field:JsonProperty("data")
    var data: Data? = null
)

@RegisterForReflection
data class Data(
    @field:JsonProperty("offset")
    var offset: Int? = null,
    @field:JsonProperty("limit")
    var limit: Int? = null,
    @field:JsonProperty("total")
    var total: Int? = null,
    @field:JsonProperty("count")
    var count: Int? = null,
    @field:JsonProperty("results")
    var results: List<Result> = mutableListOf()
)

@RegisterForReflection
data class Result(
    @field:JsonProperty("id")
    var id: Int? = null,
    @field:JsonProperty("name")
    var name: String? = null,
    @field:JsonProperty("description")
    var description: String? = null,
    @field:JsonProperty("modified")
    var modified: String? = null,
    @field:JsonProperty("thumbnail")
    var thumbnail: Thumbnail? = null,
    @field:JsonProperty("resourceURI")
    var resourceURI: URI? = null,
    @field:JsonProperty("comics")
    var comics: Collection? = null,
    @field:JsonProperty("series")
    var series: Collection? = null,
    @field:JsonProperty("stories")
    var stories: Collection? = null,
    @field:JsonProperty("events")
    var events: Collection? = null,
    @field:JsonProperty("urls")
    var urls: List<Url> = mutableListOf()
)

@RegisterForReflection
data class Thumbnail(
    @field:JsonProperty("path")
    var path: String? = null,
    @field:JsonProperty("extension")
    var extension: String? = null
)

@RegisterForReflection
data class Collection(
    @field:JsonProperty("available")
    var available: Int? = null,
    @field:JsonProperty("collectionURI")
    var collectionURI: URI? = null,
    @field:JsonProperty("items")
    var items: List<Resource> = mutableListOf(),
    @field:JsonProperty("returned")
    var returned: Int? = null
)

@RegisterForReflection
data class Resource(
    @field:JsonProperty("resourceURI")
    var resourceURI: URI? = null,
    @field:JsonProperty("name")
    var name: String? = null,
    @field:JsonProperty("type")
    var type: String? = null
)

@RegisterForReflection
data class Url(
    @field:JsonProperty("type")
    var type: String? = null,
    @field:JsonProperty("url")
    var url: URI? = null
)
