package rodyapal.mirea.model.session

import io.ktor.server.auth.*

data class UserSession(
	val name: String = "",
) : Principal
