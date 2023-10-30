package com.rodyapal.dcsa.network

import io.ktor.client.*
import io.rsocket.kotlin.ktor.client.RSocketSupport


const val RSOCKET_URL = "http://127.0.0.1:8008/rsocket"

//expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

val ktorClient = HttpClient {
//	install(WebScokets)
	install(RSocketSupport)
}