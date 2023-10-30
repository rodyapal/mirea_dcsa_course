package rodyapal.mirea.config

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import rodyapal.mirea.model.session.AdaptiveSession
import rodyapal.mirea.model.session.RedisSessionStorage
import rodyapal.mirea.model.session.UserSession

fun Application.configureSession() {
	install(Sessions) {
		cookie<UserSession>("user_session", RedisSessionStorage()) {
			cookie.maxAgeInSeconds = 86400 // 1 day
		}
		cookie<AdaptiveSession>("adaptive_session", RedisSessionStorage()) {
			cookie.maxAgeInSeconds = 120
		}
	}
}