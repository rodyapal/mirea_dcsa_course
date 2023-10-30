package com.rodyapal.dcsa.screens.all.model

sealed class AllArticlesEvent {
	data object EnterScreen : AllArticlesEvent()

	data class OnDeleteArticle(
		val id: Int
	) : AllArticlesEvent()
}