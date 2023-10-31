val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val komapper_version: String by project
val rsocket_version: String by project
val junit_version: String by project
val coroutines_test_version: String by project
val mockk_version: String by project

plugins {
	kotlin("jvm") version "1.9.10"
	id("io.ktor.plugin") version "2.3.5"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
	id("com.google.devtools.ksp") version "1.9.10-1.0.13"
}

group = "com.rodyapal"
version = "0.0.1"

application {
	mainClass.set("io.ktor.server.netty.EngineMain")

	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.ktor:ktor-server-core-jvm")
	implementation("io.ktor:ktor-server-cors-jvm")
	implementation("io.ktor:ktor-server-call-logging-jvm")
	implementation("io.ktor:ktor-server-content-negotiation-jvm")
	implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
	implementation("io.ktor:ktor-server-websockets-jvm")
	implementation("io.ktor:ktor-server-netty-jvm")
	implementation("ch.qos.logback:logback-classic:$logback_version")

	implementation("io.rsocket.kotlin:rsocket-ktor-server:$rsocket_version")


	platform("org.komapper:komapper-platform:$komapper_version").let {
		implementation(it)
		ksp(it)
	}
	implementation("org.komapper:komapper-starter-r2dbc")
	implementation("org.komapper:komapper-dialect-h2-r2dbc")
	ksp("org.komapper:komapper-processor")


	testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
	implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
	testImplementation("io.rsocket.kotlin:rsocket-ktor-client:$rsocket_version")

	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

	testImplementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit_version")

	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_test_version")

	testImplementation("io.mockk:mockk:$mockk_version")
}
