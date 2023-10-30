package com.rodyapal.dcsa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.rodyapal.dcsa.navigation.Navigation
import com.rodyapal.dcsa.navigation.rememberNavController
import com.rodyapal.dcsa.screens.Screen
import com.rodyapal.dcsa.screens.all.AllArticlesReducer
import com.rodyapal.dcsa.screens.all.AllArticlesScreen
import com.rodyapal.dcsa.theme.AppTheme
import com.rodyapal.dcsa.theme.LocalThemeIsDark
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
internal fun App(
    systemAppearance: (isLight: Boolean) -> Unit = {}
) = AppTheme(systemAppearance) {
    val navController = rememberNavController(Screen.home)
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        NavigationRail {
            Screen.all.forEach { screen ->
                NavigationRailItem(
                    selected = screen == navController.currentDestination,
                    onClick = { navController.navigate(screen) },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = null
                        )
                    }
                )
            }
        }
        Navigation(
            navController = navController
        ) { destination ->
            when (destination) {
                Screen.AllArticles -> {
                    AllArticlesScreen(
                        reducer = AllArticlesReducer(
                            scope = rememberCoroutineScope()
                        )
                    )
                }
                Screen.ArticleRequester -> TODO()
                Screen.NewArticle -> TODO()
            }
        }
    }
}