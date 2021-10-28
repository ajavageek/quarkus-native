package ch.frankel.blog

import io.quarkus.vertx.web.Route
import io.smallrye.mutiny.Uni
import io.vertx.core.http.HttpServerRequest
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.mutiny.core.Vertx
import io.vertx.mutiny.core.buffer.Buffer
import io.vertx.mutiny.ext.web.client.HttpRequest
import io.vertx.mutiny.ext.web.client.WebClient
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

class MarvelFactory {

    @Singleton
    fun digest(): MessageDigest = MessageDigest.getInstance("MD5")

    @Singleton
    fun webClient(vertx: Vertx, props: MarvelProperties): WebClient = WebClient.create(
        vertx,
        WebClientOptions()
            .setSsl(props.server.ssl)
            .setDefaultHost(props.server.host)
            .setDefaultPort(props.server.port)
    )
}

@Singleton
class MarvelRoutes @Inject constructor(
    private val client: WebClient,
    private val props: MarvelProperties,
    private val digest: MessageDigest
) {

    @Route(path = "/")
    fun characters(rc: RoutingContext): Uni<Model> = client
        .get("/v1/public/characters")
        .queryParamsWith(props, digest)
        .queryParamsWith(rc.request())
        .send()
        .onItem()
        .transform { it.bodyAsJson(Model::class.java) }
}

private fun HttpRequest<Buffer>.queryParamsWith(request: HttpServerRequest) =
    apply {
        arrayOf("limit", "offset", "orderBy").forEach { param ->
            request.getParam(param)?.let {
                addQueryParam(param, it)
            }
        }
    }

private fun HttpRequest<Buffer>.queryParamsWith(props: MarvelProperties, digest: MessageDigest) =
    apply {
        val ts = System.currentTimeMillis().toString()
        addQueryParam("ts", ts)
        addQueryParam("apikey", props.apiKey)
        val md5 = "$ts${props.privateKey}${props.apiKey}".toMd5(digest)
        addQueryParam("hash", md5)
    }

private fun String.toMd5(digest: MessageDigest) =
    BigInteger(1, digest.digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')
