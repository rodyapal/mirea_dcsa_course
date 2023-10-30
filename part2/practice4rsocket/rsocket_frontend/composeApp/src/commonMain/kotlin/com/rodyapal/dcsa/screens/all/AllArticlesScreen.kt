package com.rodyapal.dcsa.screens.all

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rodyapal.dcsa.screens.all.model.AllArticlesEvent
import com.rodyapal.dcsa.screens.all.model.AllArticlesViewState
import compose.icons.FeatherIcons
import compose.icons.feathericons.Delete

@Composable
fun AllArticlesScreen(
	reducer: AllArticlesReducer
) {
	val viewState = reducer.viewState.collectAsState()
	LaunchedEffect(Unit) {
		reducer.obtainEvent(AllArticlesEvent.EnterScreen)
	}
	when (val state = viewState.value) {
		is AllArticlesViewState.Display -> AllArticlesScreenDisplay(
			state = state,
			onDeleteArticle = { it: Int ->
				reducer.obtainEvent(AllArticlesEvent.OnDeleteArticle(it))
			}
		)
		is AllArticlesViewState.Loading -> Box(modifier = Modifier.fillMaxSize()) {
			CircularProgressIndicator(Modifier.align(Alignment.Center))
		}
	}
}

@Composable
fun AllArticlesScreenDisplay(
	state: AllArticlesViewState.Display,
	onDeleteArticle: (Int) -> Unit
) {
	LazyColumn {
		items(state.articles) { article ->
			Card(
				modifier = Modifier.fillMaxWidth()
			) {
				Row(
					modifier = Modifier.padding(12.dp),
					horizontalArrangement = Arrangement.SpaceAround
				) {
					Text(
						text = article.title,
						style = MaterialTheme.typography.titleMedium
					)
					IconButton(
						onClick = {
							onDeleteArticle(article.id)
						}
					) {
						Icon(
							imageVector = FeatherIcons.Delete,
							contentDescription = null
						)
					}
				}
				Text(
					modifier = Modifier.padding(12.dp),
					text = article.body,
					style = MaterialTheme.typography.bodyMedium
				)
			}
		}
	}
}
