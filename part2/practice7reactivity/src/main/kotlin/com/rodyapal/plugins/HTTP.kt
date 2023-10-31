package com.rodyapal.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureHTTP() {
	install(CORS) {
		allowMethod(HttpMethod.Get)
		allowHeader(HttpHeaders.Authorization)
		anyHost()
	}
}
