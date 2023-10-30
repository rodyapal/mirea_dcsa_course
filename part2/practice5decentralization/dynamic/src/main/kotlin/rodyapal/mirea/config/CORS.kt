package rodyapal.mirea.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
	install(CORS) {
		allowHeader(HttpHeaders.ContentType)
		allowMethod(HttpMethod.Options)
		allowMethod(HttpMethod.Put)
		allowMethod(HttpMethod.Delete)
		allowMethod(HttpMethod.Patch)
		allowMethod(HttpMethod.Post)
		allowMethod(HttpMethod.Get)
		allowHeader(HttpHeaders.Authorization)
		allowHeader(HttpHeaders.Cookie)
		anyHost()
		allowSameOrigin = true
		allowOrigins { it == "localhost:8001/adaptive" }
	}
}