package com.rodyapal.dcsa.screens.all

import com.rodyapal.dcsa.model.Reducer
import com.rodyapal.dcsa.network.ArticleRepository
import com.rodyapal.dcsa.screens.all.model.AllArticlesEvent
import com.rodyapal.dcsa.screens.all.model.AllArticlesViewState
import io.github.aakira.napier.log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class AllArticlesReducer(
	private val articleRepository: ArticleRepository = ArticleRepository(),
	private val scope: CoroutineScope
) : Reducer<AllArticlesEvent> {

	private val _viewState: MutableStateFlow<AllArticlesViewState> = MutableStateFlow(AllArticlesViewState.Loading)
	val viewState get() = _viewState as StateFlow<AllArticlesViewState>
	override fun obtainEvent(event: AllArticlesEvent) {
		when (event) {
			is AllArticlesEvent.EnterScreen -> scope.launch {
				articleRepository.getAllArticles().collect { article ->
					log { "$article" }
					when (val state = viewState.value) {
						is AllArticlesViewState.Display -> _viewState.update {
							state.copy(
								articles = state.articles + article
							)
						}
						is AllArticlesViewState.Loading -> _viewState.update {
							AllArticlesViewState.Display(
								articles = listOf(article)
							)
						}
					}
				}
			}
			is AllArticlesEvent.OnDeleteArticle -> scope.launch {
				articleRepository.deleteArticle(event.id)
				_viewState.update { state ->
					(state as AllArticlesViewState.Display).let {
						it.copy(
							articles = it.articles.filter { it.id != event.id }
						)
					}
				}
			}
		}
	}
}