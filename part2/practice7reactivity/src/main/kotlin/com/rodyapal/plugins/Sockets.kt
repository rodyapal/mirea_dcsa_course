package com.rodyapal.plugins

import io.ktor.server.application.*
import io.ktor.server.websocket.*
import io.rsocket.kotlin.ktor.server.RSocketSupport

fun Application.configureSockets() {
	install(WebSockets)
	install(RSocketSupport) {
		server {
			maxFragmentSize = 1024
		}
	}
}
