package rodyapal.mirea.view

import io.ktor.client.utils.*
import kotlinx.html.*

fun HTML.pdfPage(
	fileNames: List<String>
) {
	head {
		title("Pdf loader")
	}
	body {
		form(
			action = "pdf/upload",
			method = FormMethod.post,
			encType = FormEncType.multipartFormData
		) {
			p {
				fileInput(name = "file")
			}
			p {
				submitInput() {
					buildHeaders {  }
				}
			}
		}
		div {
			+"Uploaded files: "
			ul {
				for (filename in fileNames) {
					li {
						a(href = "pdf/$filename") {
							+filename
						}
					}
				}
			}
		}
	}
}