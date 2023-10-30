package rodyapal.mirea.view

import kotlinx.html.*
import rodyapal.mirea.model.database.ValuableIdDto

fun HTML.cataloguePage(
	goods: List<ValuableIdDto>
) {
	head {
		title("Goods page")
	}
	body {
		h1 {
			+"Shop goods"
		}
		div {
			style = """display: flex; flex-direction: column;"""
			for (item in goods) {
				div {
					style = """display: flex; flex-direction: row; margin: 14px;"""
					div {
						style = """margin-left: 7px;"""
						+(item.id).toString()
					}
					div {
						style = """margin-left: 7px;"""
						+item.title
					}
					div {
						style = """margin-left: 7px;"""
						+(item.cost).toString()
					}
				}
			}
		}
	}
}