package com.rodyapal.dcsa.network

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.websocket.*
import io.rsocket.kotlin.ktor.client.RSocketSupport

//actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Js) {
//	install(WebSockets)
//	install(RSocketSupport)
//}