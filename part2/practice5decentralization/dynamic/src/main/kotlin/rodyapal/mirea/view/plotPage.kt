package rodyapal.mirea.view

import kotlinx.html.*

fun HTML.plotPage(
	plotSvgData: List<String>
) {
	head {
		title("Plot page")
	}
	body {
		for (plt in plotSvgData) {
			div {
				unsafe {
					+plt
				}
			}
		}
	}
}