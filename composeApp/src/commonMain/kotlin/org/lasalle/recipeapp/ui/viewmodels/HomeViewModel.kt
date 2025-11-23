package org.lasalle.recipeapp.ui.viewmodels

import androidx.compose.material3.SheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.lasalle.recipeapp.data.services.KtorfitFactory
import org.lasalle.recipeapp.data.services.Preferences
import org.lasalle.recipeapp.models.Prompt
import org.lasalle.recipeapp.models.Recipe
import org.lasalle.recipeapp.models.RecipePreview

class HomeViewModel : ViewModel() {
    private val recipeService = KtorfitFactory.getRecipeService()
    var ingredients by mutableStateOf("")
    var generatedRecipe by mutableStateOf<RecipePreview?>(null)
    var recentRecipes by mutableStateOf<List<Recipe>>(listOf())
    var recipes by mutableStateOf<List<Recipe>>(listOf())
    var showSheet by mutableStateOf(false )
    val userId = Preferences.getUserId()
    var isLoading by mutableStateOf(false)

    init {
        loadRecipes()
    }

    fun loadRecipes(){
        viewModelScope.launch {
            try {
                isLoading = true
                val result = recipeService.getRecipesByUserId(userId)
                recipes = result
                recentRecipes = recipes.takeLast(5).reversed()
                println(result.toString())
            }
            catch (e: Exception){
                println(e.toString())
            }
            finally {
                isLoading = false
            }

        }
    }

    fun generateRecipe(onShowSheetState:() -> Unit){
        viewModelScope.launch {
            try {
                isLoading = true
                if(ingredients.isBlank()) return@launch
                val prompt = Prompt(
                    ingredients = ingredients
                )
                val result = recipeService.generateRecipe(prompt)
                generatedRecipe = result
                showSheet = true
                println(result.toString())
            }
            catch (e: Exception){
                println(e.toString())
            }
            finally {
                isLoading = false
            }
        }
    }


    fun showModalFromList(recipe: RecipePreview){
        showSheet = true
        generatedRecipe = recipe
    }

    fun saveRecipeInDb(){
        viewModelScope.launch {
            val recipe = Recipe(
                id = 0,
                category = generatedRecipe?.category ?: "",
                imageUrl = generatedRecipe?.imageUrl ?: "",
                ingredients = generatedRecipe?.ingredients ?: listOf() ,
                instructions = generatedRecipe?.instructions ?: listOf(),
                userId = userId,
                minutes = generatedRecipe?.minutes ?: 0,
                stars = generatedRecipe?.stars ?: 0,
                title = generatedRecipe?.title ?: ""

            )
            val result = recipeService.saveRecipeInDb(
                recipe
            )
            showSheet = false
            loadRecipes()
        }
    }

    fun hideModal(){
        showSheet = false
        generatedRecipe = null
    }

    fun logout(){
        Preferences.clearPreferences()
    }

}