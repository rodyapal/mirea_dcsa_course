package two

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.random.nextInt

suspend fun one(
	upperBound: Int = 510
) {
	flow {
		for (i in 1..upperBound) {
			emit(i)
		}
	}.map { it * it }
		.collect { println(it) }

	println("-------------------------------\n\n")

	flow {
		for (i in 1..upperBound) {
			emit(i)
		}
	}.filter { it < 500 }
		.collect { println(it) }

	println("-------------------------------\n\n")

	val count = flow {
		for (i in 1 .. Random.nextInt(2, upperBound)) {
			emit(i)
		}
	}.count()
	println(count)
}

suspend fun two() {
	//2.1 = 2.3
	flow {
		for (i in 1..10) {
			emit(i)
		}
	}.zip(
		flow {
			for (i in 11..20) {
				emit(i)
			}
		}
	) { a, b -> a to b }.collect { println("${it.first} | ${it.second}") }

	//2.2 - can't be done
}

suspend fun three() {
	flow {
		for (i in 1..10) {
			emit(i)
		}
	}.drop(3)
		.collect { println(it) }

	println("-------------------------------\n\n")

	flow {
		for (i in 1..10) {
			emit(i)
		}
	}.withIndex().filter { it.index < 5 }
		.collect { println(it.value) }

	println("-------------------------------\n\n")

	val last = flow {
		for (i in 1..10) {
			emit(i)
		}
	}.last()
	println(last)
}

fun main() = runBlocking {
	one()
//	two()
//	three()
}