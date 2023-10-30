package com.rodyapal.dcsa.screens.all.model

import com.rodyapal.dcsa.model.Article

sealed class AllArticlesViewState {
	data class Display(
		val articles: List<Article>
	) : AllArticlesViewState()

	data object Loading : AllArticlesViewState()
}