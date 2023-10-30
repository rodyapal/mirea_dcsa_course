package rodyapal.mirea.model.session

import io.ktor.server.sessions.*
import rodyapal.mirea.model.redis


class RedisSessionStorage : SessionStorage {
	override suspend fun invalidate(id: String): Unit = redis {
		it.expire(id, 1u)
	}

	override suspend fun read(id: String): String = redis {
		it.get(id) ?: throw NoSuchElementException("Session $id not found")
	}

	override suspend fun write(id: String, value: String): Unit = redis {
		it.set(id, value)
	}
}