package three

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextLong

suspend fun generateFile(size: Int): String {
	delay(1000)
	return List(size) { ('a'..'z').random() }.joinToString()
}

suspend fun processFile(file: String) {
	delay(file.length * 7L)
}

fun main() = runBlocking {
	flow {
		while (true) {
			emit(
				generateFile(Random.nextInt(10..100))
			)
			delay(Random.nextLong(100L..1000L))
		}
	}.collectIndexed { index, file ->
		processFile(file)
		println("File $index processed")
	}
}