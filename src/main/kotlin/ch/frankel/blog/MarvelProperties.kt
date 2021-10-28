package ch.frankel.blog

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.inject.Singleton

@Singleton
data class MarvelProperties(
    val server: ServerProperties,
    @ConfigProperty(name = "app.marvel.apiKey") val apiKey: String,
    @ConfigProperty(name = "app.marvel.privateKey") val privateKey: String
)

@Singleton
data class ServerProperties(
    @ConfigProperty(name = "app.marvel.server.ssl") val ssl: Boolean,
    @ConfigProperty(name = "app.marvel.server.host") val host: String,
    @ConfigProperty(name = "app.marvel.server.port") val port: Int
)
