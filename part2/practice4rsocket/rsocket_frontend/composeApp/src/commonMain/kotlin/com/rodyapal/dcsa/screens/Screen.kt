package com.rodyapal.dcsa.screens

import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.FeatherIcons
import compose.icons.feathericons.Edit
import compose.icons.feathericons.List
import compose.icons.feathericons.RotateCw

sealed class Screen(
	val icon: ImageVector
) {
	data object AllArticles : Screen(FeatherIcons.List)
	data object NewArticle : Screen(FeatherIcons.Edit)
	data object ArticleRequester : Screen(FeatherIcons.RotateCw)

	companion object {
		val home get() = AllArticles

		val all get() = listOf(AllArticles, NewArticle, ArticleRequester)
	}
}