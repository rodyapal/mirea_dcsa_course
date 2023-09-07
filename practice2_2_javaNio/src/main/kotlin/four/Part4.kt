package four

import ch.tutteli.niok.exists
import ch.tutteli.niok.isDirectory
import three.hash.HashUtils
import three.hash.MessageDigestAlgorithm
import two.INPUT
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.security.MessageDigest
import kotlin.io.path.fileSize
import kotlin.io.path.readBytes
import kotlin.math.max
import kotlin.math.min

const val WORKDIR = "D:/_Projects/_Mirea/mirea_dcsa_course/practice2_2_javaNio/src/main/kotlin/four/workdir"

val files = mutableMapOf<String, ByteArray>()

fun main() {
	val service = FileSystems.getDefault().newWatchService()
	val dir = Paths.get(WORKDIR)
	dir.register(
			service,
			StandardWatchEventKinds.ENTRY_MODIFY,
			StandardWatchEventKinds.ENTRY_CREATE,
			StandardWatchEventKinds.ENTRY_DELETE
	)
	Files
		.walk(dir)
		.filter { it.exists && !it.isDirectory }
		.forEach {
			files[it.fileName.toString()] = Files.readAllBytes(it)
		}
	do {
		val key = service.take()
		key.pollEvents().forEach {
			when (it.kind()) {
				StandardWatchEventKinds.ENTRY_MODIFY -> {
					println("Modify: ${it.context()}\nDiff:")
					files[it.context().toString()]?.let { old ->
						val new = dir.resolve(it.context() as Path).readBytes()
						val diff = mutableListOf<Pair<MutableList<Byte>, MutableList<Byte>>>()
						for (i in 0 until min(old.size, new.size)) {
							if (old[i] != new[i]) {
								if (i == 0 || old[i - 1] == new[i - 1]) {
									diff.add(
										mutableListOf(old[i]) to mutableListOf(new[i])
									)
								} else {
									diff.last().first.add(old[i])
									diff.last().second.add(new[i])
								}
							}
						}
						diff.forEach { bytes ->
							println("Old: ${bytes.first.toByteArray().decodeToString()} | " +
									"New: ${bytes.second.toByteArray().decodeToString()}")
						}
					}
					files[it.context().toString()] = dir.resolve(it.context() as Path).readBytes()
				}
				StandardWatchEventKinds.ENTRY_CREATE -> {
					println("Create: ${it.context()}")
					val path = dir.resolve(it.context() as Path)
					if (!Files.isDirectory(dir.resolve(path))) {
						files[it.context().toString()] = path.readBytes()
					}
				}
				StandardWatchEventKinds.ENTRY_DELETE -> {
					println("Delete: ${it.context()}\n\t" +
							"Size: ${files[it.context().toString()]?.size} bytes\n\t" +
							"MD5: ${files[it.context().toString()]?.let { fileContent ->
								HashUtils.getChecksum(
									MessageDigest.getInstance(MessageDigestAlgorithm.MD5),
									fileContent.decodeToString()
								)
							}}"
					)
					files.remove(it.context())
				}
			}
		}
		key.reset()
	} while (key != null)
}