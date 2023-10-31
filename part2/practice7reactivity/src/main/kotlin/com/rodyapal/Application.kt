package com.rodyapal

import com.rodyapal.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
	io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
	configureHTTP()
	configureMonitoring()
	configureSerialization()
	configureSockets()
	configureRouting()
}
