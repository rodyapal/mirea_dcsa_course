package rodyapal.mirea.config

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import rodyapal.mirea.model.database.UserEntity
import rodyapal.mirea.model.session.UserSession

fun Application.configureAuthentication() {
	install(Authentication) {
		form("auth-form") {
			userParamName = "username"
			passwordParamName = "password"
			validate { credentials ->
				if (UserEntity.contains(credentials.name, credentials.password)) {
					UserIdPrincipal(credentials.name)
				} else {
					null
				}
			}
		}
		session<UserSession>("auth-session") {
			validate { session ->
				if(UserEntity.contains(session.name)) {
					session
				} else {
					null
				}
			}
			challenge {
				call.respondRedirect("/login")
			}
		}
	}
}