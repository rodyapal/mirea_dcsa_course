package rodyapal.mirea.routes.view

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.tika.Tika
import rodyapal.mirea.model.file.RedisFileStorage
import rodyapal.mirea.view.pdfPage

fun Route.pdfLoadRoute() {
	route("pdf") {
		get {
			val fileNames = RedisFileStorage.getAllFilenames()
			call.respondHtml {
				pdfPage(fileNames)
			}
		}
		post("/upload") {
			val multipart = call.receiveMultipart()
			val tika = Tika()
			multipart.forEachPart { part ->
				if (part is PartData.FileItem) {
					val fileName = part.originalFileName as String
					val fileBytes = part.streamProvider().readBytes()
					val type = tika.detect(fileBytes, fileName)
					if (type.contains("pdf")) {
						RedisFileStorage.save(fileName, fileBytes)
					} else {
						call.respondText(status = HttpStatusCode.BadRequest) { "Not a pdf file" }
					}
				}
				part.dispose()
			}
			call.respondText(status = HttpStatusCode.Created) { "File successfully saved" }
		}
		get("/{filename}") {
			val filename = call.parameters["filename"] ?: return@get call.respondText(status = HttpStatusCode.BadRequest) { "No filename" }
			val file = RedisFileStorage.read(filename)
			if(file != null) {
				call.response.header("Content-Disposition", "attachment; filename=\"${file.name}\"")
				call.respondFile(file)
			}
			else call.respond(HttpStatusCode.NotFound) { "File not found" }
		}
	}
}