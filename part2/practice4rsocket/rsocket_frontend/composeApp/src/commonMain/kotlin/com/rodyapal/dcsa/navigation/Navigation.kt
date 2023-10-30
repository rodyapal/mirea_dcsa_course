package com.rodyapal.dcsa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.rodyapal.dcsa.screens.Screen

@Composable
fun Navigation(
	navController: NavController,
	content: @Composable (destination: Screen) -> Unit
) {
	val screenState = navController.screen.collectAsState()
	content(screenState.value)
}