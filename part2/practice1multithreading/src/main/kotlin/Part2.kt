import kotlinx.coroutines.*
fun main() = runBlocking {
	var job: Job? = null
	while (job == null || !job.isCompleted) {
		if (job == null) println("Enter number: ")
		val number = readln().toInt()
//		job?.cancel()
		launch(Dispatchers.Default) {
			println("Work in progress")
			delay(1000)
			println(number * number)
		}.also { job = it }
	}
}