package org.lasalle.recipeapp.models

import kotlinx.serialization.Serializable

//
@Serializable
data class RecipePreview(
    val category: String,
    val imageUrl: String?,
    val ingredients: List<String>,
    val instructions: List<String>,
    val minutes: Int,
    val prompt: String,
    val stars: Int,
    val title: String
)