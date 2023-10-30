package rodyapal.mirea.routes.view

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import rodyapal.mirea.model.session.UserSession
import rodyapal.mirea.view.loginPage

fun Route.loginRoute() {
	get("/login") {
		call.respondHtml {
			loginPage()
		}
	}
	authenticate("auth-form") {
		post("/login") {
			val userName = call.principal<UserIdPrincipal>()?.name.toString()
			call.sessions.set(
				UserSession(name = userName)
			)
			call.respondRedirect("/admin")
		}
	}
	get("/logout") {
		call.sessions.clear<UserSession>()
		call.respondRedirect("/login")
	}
}