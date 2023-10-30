package rodyapal.mirea.model

import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.KredsClient
import io.github.crackthecodeabhi.kreds.connection.newClient

const val REDIS_HOST = "redis"
const val REDIS_HOST_BACKUP = "localhost"
const val REDIS_PORT = 6379

suspend fun <T> redis(block: suspend (client: KredsClient) -> T): T = try {
	newClient(Endpoint.from("$REDIS_HOST:$REDIS_PORT")).use { block(it) }
} catch (e: Exception) {
	newClient(Endpoint.from("$REDIS_HOST_BACKUP:$REDIS_PORT")).use { block(it) }
}