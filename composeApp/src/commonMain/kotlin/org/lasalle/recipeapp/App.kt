package org.lasalle.recipeapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.lasalle.recipeapp.ui.RecipeTheme
import org.lasalle.recipeapp.ui.screens.auth.LoginScreen
import org.lasalle.recipeapp.ui.screens.auth.RegisterScreen
import org.lasalle.recipeapp.ui.screens.home.HomeScreen

@Composable
@Preview
fun App() {
    RecipeTheme {
        HomeScreen()
    }
}