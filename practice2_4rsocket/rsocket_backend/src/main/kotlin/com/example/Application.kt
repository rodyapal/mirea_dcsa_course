package com.example

import com.example.database.DatabaseFactory
import com.example.routes.configureRSockets
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.rsocket.kotlin.ktor.server.RSocketSupport

fun main() {
	embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
		.start(wait = true)
}

fun Application.module() {
	DatabaseFactory.init()

	install(WebSockets)
	install(RSocketSupport)

	routing {
		configureRSockets()
	}
}