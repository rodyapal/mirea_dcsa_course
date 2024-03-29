val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val h2_version: String by project

plugins {
	kotlin("jvm") version "1.9.0"
	id("io.ktor.plugin") version "2.3.4"
	kotlin("plugin.serialization") version "1.9.0"
}

group = "com.example"
version = "0.0.1"

application {
	mainClass.set("com.example.ApplicationKt")

	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.ktor:ktor-server-core-jvm")
	implementation("io.ktor:ktor-server-netty-jvm")
	implementation("io.ktor:ktor-server-cors:$ktor_version")
	implementation("ch.qos.logback:logback-classic:$logback_version")

	// RSocket
	implementation("io.rsocket.kotlin:rsocket-core:0.15.4")
	implementation("io.rsocket.kotlin:rsocket-ktor-server:0.15.4")

	// Exposed
	implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
	implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
	implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

	// H2 database
	implementation("com.h2database:h2:$h2_version")

	// Serialization
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

	testImplementation("io.ktor:ktor-server-tests-jvm")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
