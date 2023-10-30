package two

import kotlinx.coroutines.runBlocking
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import kotlin.time.measureTime

const val INPUT = "D:/_Projects/_Mirea/mirea_dcsa_course/practice2_2_javaNio/src/main/kotlin/two/input.txt"
const val OUTPUT = "D:/_Projects/_Mirea/mirea_dcsa_course/practice2_2_javaNio/src/main/kotlin/two/output.txt"

fun clearOutput() {
	File(OUTPUT).writeText("")
}

fun streams() {
	val input = FileInputStream(INPUT)
	val output = FileOutputStream(OUTPUT)
	output.write(
		input.readAllBytes()
	)
	input.close()
	output.close()
}

fun channel() {
	val input = AsynchronousFileChannel.open(
		Path.of(INPUT),
		StandardOpenOption.READ,
	)
	val output = AsynchronousFileChannel.open(
		Path.of(OUTPUT),
		StandardOpenOption.WRITE
	)
	val buffer = ByteBuffer.allocate(1024)
	for (i in 0 until 10240) {
		input.read(buffer, 1024L * i).get()
		buffer.flip()
		output.write(
			buffer, 1024L * i
		).get()
		buffer.clear()
	}
	input.close()
	output.close()
}

fun okio() {
	val sink = FileSystem.SYSTEM.sink(OUTPUT.toPath()).buffer()
	val source = FileSystem.SYSTEM.source(INPUT.toPath()).buffer()
	while (!source.exhausted()) {
		sink.write(
			source.readByteArray()
		)
	}
	sink.close()
	source.close()
}

fun files() {
	Files.copy(Path.of(INPUT), Path.of(OUTPUT), StandardCopyOption.REPLACE_EXISTING)
}

fun main() = runBlocking {
	measureTime {
		streams()
	}.let {
		println("Streams: $it")
	}
	clearOutput()

	measureTime {
		channel()
	}.let {
		println("Channel: $it")
	}
	clearOutput()

	measureTime {
		okio()
	}.let {
		println("Okio: $it")
	}
	clearOutput()

	measureTime {
		files()
	}.let {
		println("Files: $it")
	}
	clearOutput()
}