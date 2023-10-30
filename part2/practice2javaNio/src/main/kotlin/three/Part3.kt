package three

import three.hash.HashUtils
import three.hash.MessageDigestAlgorithm
import java.io.File
import java.security.MessageDigest


fun main() {
	val checkSumMD5 = HashUtils.getCheckSumFromFile(
		MessageDigest.getInstance(MessageDigestAlgorithm.MD5),
		File("D:/_Projects/_Mirea/mirea_dcsa_course/practice2_2_javaNio/src/main/kotlin/three/input.txt")
	)
	println(checkSumMD5)
}