package rodyapal.mirea

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*
import rodyapal.mirea.config.*
import rodyapal.mirea.model.database.AppDb
import rodyapal.mirea.model.file.RedisFileStorage

const val APP_PORT = 8080
const val APP_HOST = "0.0.0.0"

fun main() {
	embeddedServer(Tomcat, port = APP_PORT, host = APP_HOST, module = Application::module)
		.start(wait = true)
}

fun Application.module() {
	AppDb.setup()
	RedisFileStorage.setup()
	configureAuthentication()
	configureSession()
	configureTemplating()
	configureSerialization()
	configureRouting()
	configureCORS()
}
