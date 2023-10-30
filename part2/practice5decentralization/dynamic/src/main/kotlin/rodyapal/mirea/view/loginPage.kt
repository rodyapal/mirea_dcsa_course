package rodyapal.mirea.view

import kotlinx.html.*

fun HTML.loginPage() {
	body {
		form(action = "/login", encType = FormEncType.applicationXWwwFormUrlEncoded, method = FormMethod.post) {
			p {
				+"Username:"
				textInput(name = "username")
			}
			p {
				+"Password:"
				passwordInput(name = "password")
			}
			p {
				submitInput { value = "Login" }
			}
		}
	}
}