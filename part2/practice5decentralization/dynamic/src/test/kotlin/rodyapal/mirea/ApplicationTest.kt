package rodyapal.mirea

import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import rodyapal.mirea.config.*

class ApplicationTest {
	@Test
	fun testRoot() = testApplication {
		application {
			configureRouting()
		}
		client.get("/").apply {
			assertEquals(HttpStatusCode.OK, status)
			assertEquals("Hello World!", bodyAsText())
		}
	}
}