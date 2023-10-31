package com.rodyapal.plugins

import com.rodyapal.routes.songRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
	routing {
		route("/") {
			get {
				call.respond(message = "Ready: practice7reactivity", status = HttpStatusCode.OK)
			}
		}
		route("api/v1/songs") {
			songRoute()
		}
	}
}
