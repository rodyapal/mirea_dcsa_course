package rodyapal.mirea.config

import io.ktor.server.application.*
import io.ktor.server.routing.*
import rodyapal.mirea.routes.api.userApiRouting
import rodyapal.mirea.routes.api.valuablesApiRoute
import rodyapal.mirea.routes.view.*

fun Application.configureRouting() {
	routing {
		valuablesApiRoute()
		userApiRouting()
		adaptivePageRoute()
		pdfLoadRoute()
		plotPageRoute()
		adminPageRoute()
		loginRoute()
		catalogueRoute()
	}
}
