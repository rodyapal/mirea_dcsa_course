package one

import java.nio.file.Path
import kotlin.io.path.readBytes

fun main(args: Array<String>) {
	val content = Path.of(
		"D:/_Projects/_Mirea/mirea_dcsa_course/practice2_2_javaNio/src/main/kotlin/one/Part1_Input.txt"
	).readBytes().decodeToString()
	println(content)
}