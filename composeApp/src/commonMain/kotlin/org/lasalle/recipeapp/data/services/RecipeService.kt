package org.lasalle.recipeapp.data.services

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query
import io.ktor.http.cio.Request
import org.lasalle.recipeapp.models.Prompt
import org.lasalle.recipeapp.models.Recipe
import org.lasalle.recipeapp.models.RecipePreview

interface RecipeService {

    @GET("recipes")
    suspend fun getRecipesByUserId(@Query("userId")usrtId: Int): List<Recipe>

    @POST("recipes/ai-generate")
    suspend fun generateRecipe(@Body request: Prompt) : RecipePreview
}