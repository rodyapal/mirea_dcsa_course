package rodyapal.mirea.view

import kotlinx.html.*
import rodyapal.mirea.model.adaptive.*

fun HTML.adaptivePage(
	name: String,
	isRu: Boolean,
	isDark: Boolean
) {
	head {
		title(
			if (isRu) TEXT_RU_TITLE else TEXT_EN_TITLE
		)
	}
	body {
		style = if (isDark) STYLE_DARK_BODY else STYLE_LIGHT_BODY
		p {
			style = STYLE_TEXT
			+"${if (isRu) TEXT_RU_GREETING else TEXT_EN_GREETING} $name"
		}
		form(
			action = "/adaptive",
			method = FormMethod.post
		) {
			style = STYLE_TEXT
			div {
				+(if (isRu) TEXT_RU_USERNAME else TEXT_EN_USERNAME)
				textInput(name = "username") {
					style = STYLE_INPUT
				}
			}
			div {
				+(if (isRu) TEXT_RU_THEME else TEXT_EN_THEME)
				for (i in AdaptivePageParams.Themes.values()) {
					radioInput(name = i.name) {
						+i.name
					}
				}
			}
			div {
				+(if (isRu) TEXT_RU_LANG else TEXT_EN_LANG)
				for (i in AdaptivePageParams.Langs.values()) {
					radioInput(name = i.name) {
						+i.name
					}
				}
			}
			div {
				submitInput {
					style = if (isDark) STYLE_DARK_BUTTON else STYLE_LIGHT_BUTTON
				}
			}
		}
	}
}