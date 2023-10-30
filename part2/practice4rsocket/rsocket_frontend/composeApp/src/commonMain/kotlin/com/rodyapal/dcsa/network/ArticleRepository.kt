package com.rodyapal.dcsa.network

import com.rodyapal.dcsa.model.Article
import io.github.aakira.napier.log
import io.ktor.client.*
import io.rsocket.kotlin.ktor.client.rSocket
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ArticleRepository(
	private val client: HttpClient = ktorClient
) {
	suspend fun getAllArticles(): Flow<Article> = client
		.rSocket(RSOCKET_URL)
		.requestStream(
			buildPayload {
				data("""{ "data": "init" }""")
			}
		).map {
			Json.decodeFromString<Article>(it.data.readText())
		}
		.onEach {
			log { "$it" }
		}

	suspend fun deleteArticle(id: Int) = client
		.rSocket(RSOCKET_URL)
		.fireAndForget(
			buildPayload {
				data("$id")
			}
		)

	suspend fun addNewArticle(
		title: String,
		content: String
	): Article = client
		.rSocket(RSOCKET_URL)
		.requestResponse(
			buildPayload {
				Json.encodeToString(
					listOf(title, content)
				)
			}
		).let {
			Json.decodeFromString(it.data.readText())
		}

	suspend fun getArticleChannel(
		idFlow: Flow<Int>
	): Flow<Article> = client
		.rSocket(RSOCKET_URL)
		.requestChannel(
			buildPayload { data("""{ "data": "init" }""") },
			idFlow.map {
				buildPayload { data("""{ "id": "$it" }""") }
			}
		).map {
			Json.decodeFromString<Article>(it.data.readText())
		}
}