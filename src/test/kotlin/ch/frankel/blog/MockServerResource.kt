package ch.frankel.blog

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.testcontainers.containers.MockServerContainer
import org.testcontainers.utility.DockerImageName

class MockServerResource : QuarkusTestResourceLifecycleManager {

    private val mockServer = MockServerContainer(
        DockerImageName.parse("mockserver/mockserver")
    )

    override fun start(): Map<String, String> {
        mockServer.start()
        val mockServerClient = MockServerClient(mockServer.containerIpAddress, mockServer.serverPort)
        val sample = this::class.java.classLoader.getResource("sample.json")?.readText()
        mockServerClient.`when`(
            HttpRequest.request()
                .withMethod("GET")
                .withPath("/v1/public/characters")
        ).respond(
            HttpResponse()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody(sample)
        )
        return mapOf(
            "app.marvel.server.ssl" to "false",
            "app.marvel.server.host" to mockServer.containerIpAddress,
            "app.marvel.server.port" to mockServer.serverPort.toString()
        )
    }

    override fun stop() = mockServer.stop()
}