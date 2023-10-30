package com.example.routes

import com.example.database.ArticleDAO
import com.example.database.DefaultArticleDAO
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.emitOrClose
import io.rsocket.kotlin.ktor.server.rSocket
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Routing.configureRSockets() {
	val dao = DefaultArticleDAO() as ArticleDAO
	rSocket("rsocket") {
		RSocketRequestHandler {
			fireAndForget { request: Payload ->
				request.data.readText().toIntOrNull()?.let {
					dao.deleteArticle(it)
				}
			}
			requestResponse {
				val data = Json.decodeFromString<List<String>>(it.data.readText())
				val article = dao.addNewArticle(
					title = data.first(),
					body = data.last()
				)
				buildPayload {
					data(
						Json.encodeToString(article)
					)
				}
			}
			requestStream {
				val articles = dao.allArticles()
				flow {
					articles.forEach {
						emit(
							buildPayload {
								data(Json.encodeToString(it))
							}
						)
//						delay(100)
					}
				}
			}
			requestChannel { initPayload, payloads ->
				var prefix = initPayload.data.readText()

				payloads.onEach { payload ->
					prefix = payload.data.readText()
					println("Received extra payload: '$prefix'")
				}.launchIn(this)

				flow {
					while (prefix != "Terminate") {
						val id = prefix.filter { it.isDigit() }.toInt()
						val data = dao.article(id)
						println("Emitting $data with id: $id")
						emitOrClose(buildPayload { data(Json.encodeToString(data)) })
						delay(200)
					}
				}.onCompletion { throwable ->
					if (throwable is CancellationException) {
						println("Connection terminated")
					}
				}
			}
		}
	}
}

private suspend fun FlowCollector<Payload>.emitDataContinuously(
	id: Int,
	dao: ArticleDAO
) {
	while (true) {
		val data = dao.article(id)
		println("Emitting $data with id: $id")
		emitOrClose(buildPayload { data(Json.encodeToString(data)) })
		delay(200)
	}
}