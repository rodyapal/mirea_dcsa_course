import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.time.measureTime

fun List<Int>.maxItem(): Int {
	var max = Int.MIN_VALUE
	forEach {
		if (it > max) max = it
	}
	return max
}

fun main(args: Array<String>) {
	val input = List(10_000) { Random.nextInt() }
	val sequential = measureTime {
		input.maxItem()
	}
	println(
		"Sequential: $sequential"
	)
	thread {
		val inThread = measureTime {
			input.maxItem()
		}
		println(
			"Thread: $inThread"
		)
	}
	runBlocking {
		val coroutine = measureTime {
			input.maxItem()
		}
		println("Coroutine $coroutine")
	}
}