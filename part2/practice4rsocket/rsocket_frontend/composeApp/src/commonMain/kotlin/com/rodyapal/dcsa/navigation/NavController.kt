package com.rodyapal.dcsa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.rodyapal.dcsa.screens.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NavController(
	startDestination: Screen
) {
	private val _screen = MutableStateFlow(startDestination)
	val screen get() = _screen as StateFlow<Screen>

	val currentDestination get() = screen.value

	fun navigate(destination: Screen) = _screen.update { destination }
}

@Composable
fun rememberNavController(startDestination: Screen) = remember { NavController(startDestination) }
