package rodyapal.mirea.model.file

import kotlinx.coroutines.runBlocking
import rodyapal.mirea.model.redis
import java.io.File
import java.util.*

private const val FILE_PREFIX = "file_"

object RedisFileStorage {
	private val ids: Vector<String> = Vector<String>()
	val fileNames: List<String> get() = ids.toList()
	suspend fun save(file: File) = save(file.name, file.readBytes())

	suspend fun save(name: String, bytes: ByteArray) = redis { client ->
		val key = if (name.contains(FILE_PREFIX)) name else "$FILE_PREFIX${name}"
		ids.add(key)
		client.set(key, bytes.joinToString(separator = " ") { "$it" })
	}

	suspend fun delete(name: String) = redis {
		val key = if (name.contains(FILE_PREFIX)) name else "$FILE_PREFIX$name"
		it.del(key)
	}

	suspend fun read(name: String): File? = redis { client ->
		val key = if (name.contains(FILE_PREFIX)) name else "$FILE_PREFIX$name"
		if (!ids.contains(key)) return@redis null
		File(name).apply {
			client.get(key)?.let { byteString ->
				writeBytes(
					byteString.split(" ").map { it.toByte() }.toByteArray()
				)
			}
		}
	}

	suspend fun getAllFilenames(): List<String> = redis { client ->
		client.keys("$FILE_PREFIX*")
	}

	fun setup() = runBlocking {
		redis { client ->
			client.keys("$FILE_PREFIX*").forEach { key ->
				ids.add(key)
			}
		}
	}
}