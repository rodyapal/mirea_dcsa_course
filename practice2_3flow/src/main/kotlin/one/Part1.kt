package one

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.random.nextInt

const val MAX_TEMPERATURE = 25
const val MAX_CO2 = 70

fun main() = runBlocking {
	val temperatureFlow: Flow<Int> = flow {
		while (true) {
			emit(Random.nextInt(15..30))
			delay(1000)
		}
	}
	val co2Flow = flow {
		while (true) {
			emit(Random.nextInt(30..100))
			delay(1000)
		}
	}

	temperatureFlow.combine(co2Flow) { temperature, co2 -> temperature to co2 }
		.collect { (temperature, co2) ->
			if (temperature >= MAX_TEMPERATURE && co2 >= MAX_CO2) {
				println("ALARM! Temperature: $temperature | CO2: $co2")
			} else {
				if (temperature >= MAX_TEMPERATURE) {
					println("Warning! High temperature: $temperature")
				}
				if (co2 >= MAX_CO2) {
					println("Warning! High CO2: $co2")
				}
			}
		}
}