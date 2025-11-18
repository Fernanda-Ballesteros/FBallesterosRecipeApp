package org.lasalle.recipeapp.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.lasalle.recipeapp.models.Recipe
import org.lasalle.recipeapp.ui.RecipeTheme

@Composable
fun LoadingOverlay(){
    val colors = MaterialTheme.colorScheme
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween( 1000, easing = LinearEasing)
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background.copy(0.4f)),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Restaurant,
                contentDescription = null,
                modifier = Modifier
                .size(70.dp)
                .graphicsLayer(rotationZ = rotation),
                tint = colors.primary
            )

            Text(
                text = "Cocinando...",
                style = MaterialTheme.typography.titleMedium,
                color = colors.onSurface
            )
        }
    }
}

@Preview
@Composable
fun LoadingOverlayPreview(){
    RecipeTheme {
        LoadingOverlay()
    }
}