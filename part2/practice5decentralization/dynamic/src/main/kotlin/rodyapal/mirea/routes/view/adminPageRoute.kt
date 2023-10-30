package rodyapal.mirea.routes.view

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import rodyapal.mirea.model.database.UserEntity
import rodyapal.mirea.model.session.UserSession
import rodyapal.mirea.view.adminPage

fun Route.adminPageRoute() {
	route("/admin") {
		authenticate("auth-session") {
			get {
				val userSession = call.principal<UserSession>() ?: return@get call.respondText(status = HttpStatusCode.Unauthorized) { "Invalid session" }
				val users = transaction { UserEntity.all().map { it.idData } }.toList()
				call.respondHtml {
					adminPage(userSession.name, users)
				}
			}
		}
	}
}