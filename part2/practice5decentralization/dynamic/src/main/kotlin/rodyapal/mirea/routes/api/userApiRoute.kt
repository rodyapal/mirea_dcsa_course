package rodyapal.mirea.routes.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import rodyapal.mirea.model.database.AppDb.query
import rodyapal.mirea.model.database.UserDto
import rodyapal.mirea.model.database.UserEntity

fun Route.userApiRouting() {
	route("/api/v1/user") {
		get {
			call.parameters["id"]?.toIntOrNull()?.let {
				try {
					call.respond(query { UserEntity[it].idData })
				} catch (e: EntityNotFoundException) {
					call.respondText(status = HttpStatusCode.BadRequest) { "Invalid id" }
				}
			} ?: query { UserEntity.all().map { it.idData } }.let {
				if (it.isNotEmpty()) {
					call.respond(it)
				} else {
					call.respondText("No users found", status = HttpStatusCode.OK)
				}
			}
		}
		post {
			val user = call.receive<UserDto>()
			query {
				UserEntity.new {
					name = user.name
					password = user.password
				}
			}
			call.respondText("User stored correctly", status = HttpStatusCode.Created)
		}
		delete {
			call.parameters["id"]?.toIntOrNull()?.let {
				query { UserEntity[it].delete() }
				call.respondText(status = HttpStatusCode.OK) { "Deleted" }
			} ?: call.respondText("Invalid id param", status = HttpStatusCode.BadRequest)
		}
		patch {
			val id = call.parameters["id"]?.toIntOrNull() ?: return@patch call.respondText(
				"Invalid id param",
				status = HttpStatusCode.BadRequest
			)
			query {
				UserEntity[id].let { entity ->
					call.parameters["name"]?.let { entity.name = it }
					call.parameters["password"]?.let { entity.password = it }
				}
			}
			call.respondText(status = HttpStatusCode.OK) { "Updated" }
		}
	}
}