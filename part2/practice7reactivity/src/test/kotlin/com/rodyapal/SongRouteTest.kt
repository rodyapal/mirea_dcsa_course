package com.rodyapal

import com.rodyapal.dto.SearchRequest
import com.rodyapal.dto.SongDto
import com.rodyapal.entity.Song
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.rsocket.kotlin.ktor.client.RSocketSupport
import io.rsocket.kotlin.ktor.client.rSocket
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runners.Parameterized.BeforeParam
import kotlin.test.assertEquals

class SongRouteTest {
	private val appConfig = ApplicationConfig("application.conf")
	@Test
	fun testGetById() = testApplication {
		environment { appConfig }
		recreateTable()

		val badResponse = client.get("api/v1/songs/25")
		assertEquals(HttpStatusCode.BadRequest, badResponse.status)

		val goodResponse = client.get("api/v1/songs/5").let {
			Json.decodeFromString<Song>(it.bodyAsText())
		}
		assertEquals("Yesterday", goodResponse.title)
	}

	@Test
	fun testAdd() = testApplication {
		environment { appConfig }
		recreateTable()

		val dto = SongDto("test title", "test artist", 0, "test album", "test publisher")
		val song = client.post("api/v1/songs") {
			header(HttpHeaders.ContentType, ContentType.Application.Json)
			setBody(Json.encodeToString(dto))
		}.let {
			Json.decodeFromString<Song>(it.bodyAsText())
		}
		assertEquals("test title", song.title)
	}

	@Test
	fun testGetAll() = testApplication {
		environment { appConfig }
		recreateTable()

		val client = createClient {
			install(WebSockets)
			install(RSocketSupport)
		}
		val stream = client.rSocket("api/v1/songs/all").requestStream(buildPayload { data("") })
			.map { Json.decodeFromString<Song>(it.data.readText()) }
			.toList()
		assertEquals("Yesterday", stream[4].title)
		assertEquals(19, stream.size)
	}

	@Test
	fun testSearchAlbum() = testApplication {
		environment { appConfig }
		recreateTable()

		val client = createClient {
			install(WebSockets)
			install(RSocketSupport)
		}
		val result = client.rSocket("api/v1/songs/album").requestStream(buildPayload {
			data(Json.encodeToString(SearchRequest("Night")))
		})
			.map { Json.decodeFromString<Song>(it.data.readText()) }
			.toList()
		assertEquals("Bohemian Rhapsody", result.first().title)
		assertEquals(1, result.size)
	}

	@Test
	fun testSearchArtist() = testApplication {
		environment { appConfig }
		recreateTable()

		val client = createClient {
			install(WebSockets)
			install(RSocketSupport)
		}
		val result = client.rSocket("api/v1/songs/artist").requestStream(buildPayload {
			data(Json.encodeToString(SearchRequest("The")))
		})
			.map { Json.decodeFromString<Song>(it.data.readText()) }
			.toList()
		assertEquals("Yesterday", result.first().title)
		assertEquals(3, result.size)
	}

	@Test
	fun testSearchTitle() = testApplication {
		environment { config }
		recreateTable()

		val client = createClient {
			install(WebSockets)
			install(RSocketSupport)
		}
		val result = client.rSocket("api/v1/songs/title").requestStream(buildPayload {
			data(Json.encodeToString(SearchRequest("ike")))
		})
			.map { Json.decodeFromString<Song>(it.data.readText()) }
			.toList()
		assertEquals("Like a Rolling Stone", result.first().title)
		assertEquals(2, result.size)
	}
}