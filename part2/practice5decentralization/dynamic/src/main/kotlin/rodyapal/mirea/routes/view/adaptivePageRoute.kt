package rodyapal.mirea.routes.view

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import rodyapal.mirea.model.session.AdaptiveSession
import rodyapal.mirea.view.adaptivePage

fun Route.adaptivePageRoute() {
	route("/adaptive") {
		get {
			val session = call.sessions.getOrSet { AdaptiveSession() }
			call.respondHtml {
				adaptivePage(
					name = session.name ?: "",
					isRu = session.isRu,
					isDark = session.isDark
				)
			}
		}
		post {
			var current = call.sessions.getOrSet { AdaptiveSession() }
			val params = call.receiveParameters()
			call.sessions.set(
				current.copy(
					name = params["username"],
					isRu = params["RU"]?.let { true } ?: params["EN"]?.let { false } ?: current.isRu,
					isDark = params["DARK"]?.let { true } ?: params["LIGHT"]?.let { false } ?: current.isDark,
				).also { current = it }
			)
			call.respondHtml {
				adaptivePage(
					name = current.name ?: "",
					isRu = current.isRu,
					isDark = current.isDark
				)
			}
		}
		get("log") {
			call.respondText(
				call.sessions.get<AdaptiveSession>()?.toString() ?: "Null session"
			)
		}
	}
}