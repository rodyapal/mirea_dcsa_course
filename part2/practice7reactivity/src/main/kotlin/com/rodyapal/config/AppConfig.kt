package com.rodyapal.config

import io.ktor.server.config.*

object AppConfig {
    val applicationConfiguration: ApplicationConfig = ApplicationConfig("application.conf")
    val dbUrl: String get() = applicationConfiguration.property("database.url").getString()
}