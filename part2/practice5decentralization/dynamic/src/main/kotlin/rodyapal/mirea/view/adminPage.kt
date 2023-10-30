package rodyapal.mirea.view

import kotlinx.html.*
import rodyapal.mirea.model.database.UserIdDto

fun HTML.adminPage(
	username: String,
	users: List<UserIdDto>
) {
	head {
		title("Admin page")
	}
	body {
		h1 { +"Hello $username" }
		h2 { +"User list:" }
		div {
			style = """display: flex; flex-direction: column;"""
			for (user in users) {
				div {
					style = """display: flex; flex-direction: row; margin: 14px;"""
					div {
						style = """margin-left: 7px;"""
						+(user.id).toString()
					}
					div {
						style = """margin-left: 7px;"""
						+user.name
					}
					div {
						style = """margin-left: 7px;"""
						+user.password
					}
				}
			}
		}
	}
}