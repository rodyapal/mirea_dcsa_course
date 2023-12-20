import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.websocket.*
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.ktor.client.RSocketSupport
import io.rsocket.kotlin.ktor.client.rSocket
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend fun stream(rSocket: RSocket) {
	val stream: Flow<Payload> = rSocket.requestStream(buildPayload { data("Hello") })

	stream.collect { payload: Payload ->
		println(payload.data.readText())
	}
}

suspend fun response(rSocket: RSocket) {
	val response = rSocket.requestResponse(
		buildPayload {
			data(Json.encodeToString(listOf("Custom title", "Long custom body")))
		}
	)
	println(response.data.readText())
}

suspend fun fireAndForget(rSocket: RSocket) {
	rSocket.fireAndForget(
		buildPayload {
			data("57")
		}
	)
}

suspend fun channel(rSocket: RSocket) {
	val responses = rSocket.requestChannel(
		buildPayload { data("Requested: 1") },
		payloads = flow {
			(1..6	).forEach {
				emit(
					buildPayload { data("Requested: $it") }
				)
				delay(200)
			}
			emit(
				buildPayload { data("Terminate") }
			)
		}
	)
	responses.map {
		it.data.readText()
	}.onCompletion {
		println("Connection terminated")
	}.collect {
		println(it)
	}
}

fun main(args: Array<String>) = runBlocking {
	val client = HttpClient(CIO) {
		install(WebSockets)
		install(RSocketSupport)
	}
	val rSocket: RSocket = client.rSocket(path = "rsocket", port = 8008)
	response(rSocket)
	client.close()
}