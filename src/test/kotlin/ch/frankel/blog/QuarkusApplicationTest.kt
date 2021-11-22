package ch.frankel.blog

import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@QuarkusTest
@QuarkusTestResource(MockServerResource::class)
class QuarkusApplicationTest {

    @Test
    fun `should deserialize JSON payload from server and serialize it back again`() {
        val model = given()
            .`when`()
            .get("/")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .and()
            .extract()
            .`as`(Model::class.java)
        assertNotNull(model)
        assertNotNull(model.data)
        assertEquals(1, model.data.count)
        assertEquals("Anita Blake", model.data.results.first().name)
    }
}
