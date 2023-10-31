package com.rodyapal.routes

import com.rodyapal.dto.SearchRequest
import com.rodyapal.entity.DefaultSongRepository
import com.rodyapal.dto.SongDto
import com.rodyapal.entity.SongRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.ktor.server.rSocket
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.songRoute(
	repository: SongRepository = DefaultSongRepository()
) {
	get("/{id}") {
		call.parameters["id"]?.toLongOrNull()?.let { id ->
			repository.getById(id)?.let {
				call.respond(Json.encodeToString(it))
			} ?: call.respond(message = "No song with such id", status = HttpStatusCode.BadRequest)
		} ?: call.respond(message = "Null id", status = HttpStatusCode.BadRequest)
	}
	post {
		call.receive<SongDto>().let { dto ->
			repository.add(dto).let {
				call.respond(Json.encodeToString(it))
			}
		}
	}
	rSocket("/all") {
		RSocketRequestHandler {
			requestStream {
				repository.getAll().map {
					buildPayload { data(Json.encodeToString(it)) }
				}
			}
		}
	}
	rSocket("/album") {
		RSocketRequestHandler {
			requestStream {
				repository.searchByAlbum(
					Json.decodeFromString<SearchRequest>(it.data.readText()).query
				).map {
					buildPayload { data(Json.encodeToString(it)) }
				}
			}
		}
	}
	rSocket("/artist") {
		RSocketRequestHandler {
			requestStream {
				repository.searchByArtist(
					Json.decodeFromString<SearchRequest>(it.data.readText()).query
				).map {
					buildPayload { data(Json.encodeToString(it)) }
				}
			}
		}
	}
	rSocket("/title") {
		RSocketRequestHandler {
			requestStream {
				repository.searchByTitle(
					Json.decodeFromString<SearchRequest>(it.data.readText()).query
				).map {
					buildPayload { data(Json.encodeToString(it)) }
				}
			}
		}
	}
	get("/test") {
		call.respondText(
			Json.encodeToString(repository.getAll().toList())
		)
	}
}