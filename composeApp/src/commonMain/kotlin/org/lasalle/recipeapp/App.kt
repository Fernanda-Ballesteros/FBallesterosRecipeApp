package org.lasalle.recipeapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.lasalle.recipeapp.data.services.Preferences
import org.lasalle.recipeapp.ui.HomeScreenRoute
import org.lasalle.recipeapp.ui.LoginScreenRoute
import org.lasalle.recipeapp.ui.RecipeTheme
import org.lasalle.recipeapp.ui.RegisterScreenRoute
import org.lasalle.recipeapp.ui.screens.auth.LoginScreen
import org.lasalle.recipeapp.ui.screens.auth.RegisterScreen
import org.lasalle.recipeapp.ui.screens.home.HomeScreen
import org.lasalle.recipeapp.ui.viewmodels.AuthViewModel

@Composable
@Preview
fun App() {
    RecipeTheme {
        val navController = rememberNavController()
        val isLogged = Preferences.getIsLogged()
        NavHost(
            navController = navController,
            startDestination = if (isLogged) HomeScreenRoute else LoginScreenRoute
        ) {
            composable<LoginScreenRoute> {
                LoginScreen(navController)
            }

            composable<RegisterScreenRoute> {
                RegisterScreen(navController)
            }

            composable<HomeScreenRoute> {
                HomeScreen(navController)
            }
        }
    }
}