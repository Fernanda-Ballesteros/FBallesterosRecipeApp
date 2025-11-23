package org.lasalle.recipeapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.lasalle.recipeapp.models.RecipePreview
import org.lasalle.recipeapp.ui.HomeScreenRoute
import org.lasalle.recipeapp.ui.LoginScreenRoute
import org.lasalle.recipeapp.ui.RecipeTheme
import org.lasalle.recipeapp.ui.components.LoadingOverlay
import org.lasalle.recipeapp.ui.screens.home.components.RecipeCard
import org.lasalle.recipeapp.ui.viewmodels.HomeViewModel
import org.lasalle.recipeapp.utils.hideKeyboard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val colors = MaterialTheme.colorScheme
    val container = if (isSystemInDarkTheme()) colors.surface else Color.White
    val vm: HomeViewModel = viewModel()
    val focusManager = LocalFocusManager.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(15.dp)
    ) {
        //HEADER
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Hola",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Fernanda Ballesteros",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(
                            colors.primary.copy(alpha = 0.2f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "F",
                        color = colors.primary
                    )
                }

                IconButton(
                    onClick = {
                        vm.logout()
                        navController.navigate(LoginScreenRoute){
                            popUpTo(HomeScreenRoute){
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Cerrar sesion",
                        tint = colors.primary
                    )
                }


            }
        }

        // Generate Recipe
        item {
            Spacer(Modifier.height(15.dp))
            Text(
                text = "Crea, cocina, comparte y disfruta",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                style = MaterialTheme.typography.displaySmall
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                value = vm.ingredients,
                onValueChange = { vm.ingredients = it },
                shape = CircleShape,
                singleLine = true,
                placeholder = { Text("Escribe tus ingredientes...", style = MaterialTheme.typography.labelLarge) },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            hideKeyboard(
                                focusManager = focusManager
                            )
                            vm.generateRecipe() {
                                scope.launch {
                                    sheetState.partialExpand()
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Generar Receta",
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(colors.primary)
                                .padding(5.dp)

                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = container,
                    unfocusedContainerColor = container,
                    disabledContainerColor = container,
                    errorContainerColor = container,
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.primary.copy(alpha = 0.6f),
                    cursorColor = colors.primary,
                    focusedTextColor = colors.onSurface,
                    unfocusedTextColor = colors.onSurface,
                    focusedPlaceholderColor = colors.onSurfaceVariant,
                    unfocusedPlaceholderColor = colors.onSurfaceVariant
                )


            )
        }
        //


        // tus recetas recientes
        item {
            Text(text = "Tus recetas recientes", color = colors.onSurface, style = MaterialTheme.typography.titleLarge)
            LazyRow(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(vm.recentRecipes) { recipe ->
                    RecipeCard(
                        recipe,
                        onClick = {
                            scope.launch {
                                val recipePreview = RecipePreview(
                                    title = recipe.title,
                                    category = recipe.category,
                                    minutes = recipe.minutes,
                                    ingredients = recipe.ingredients,
                                    instructions = recipe.instructions,
                                    imageUrl = recipe.imageUrl,
                                    stars = recipe.stars,
                                    prompt = ""
                                )
                                vm.showModalFromList(
                                    recipe = recipePreview
                                )
                                sheetState.partialExpand()
                            }
                        }
                    )
                }
            }

        }

        //ideas rapidas
        item() {
            Spacer(Modifier.height(15.dp))
            val tags = listOf(
                "Rapidas (10 min)",
                "Pocas calorias",
                "Sin horno",
                "Desayunno"
            )
            Text(
                text = "Ideas Rapidas",
                style = MaterialTheme.typography.titleLarge,
                color = colors.onSurface,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(10.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(tags) { tag ->
                    Text(
                        text = tag,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colors.primary.copy(alpha = 0.1f))
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        color = colors.primary
                    )

                }
            }

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(colors.surfaceVariant)
                    .clickable {  }
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "¿No sabes qué cocinar hoy?" ,
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.onSurface
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Genera una receta aleatoria",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Generate random recipe",
                        tint = colors.primary,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        // todas tus recetas
        item {
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Todas tus recetas",
                        style = MaterialTheme.typography.titleLarge,
                color = colors.onSurface
            )
            Spacer(Modifier.height(12.dp))
        }

        items(vm.recentRecipes) { recipe ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(container)
                    .clickable {
                        scope.launch {
                            val recipePreview = RecipePreview(
                                title = recipe.title,
                                category = recipe.category,
                                minutes = recipe.minutes,
                                ingredients = recipe.ingredients,
                                instructions = recipe.instructions,
                                imageUrl = recipe.imageUrl,
                                stars = recipe.stars,
                                prompt = ""
                            )
                            vm.showModalFromList(recipe = recipePreview)
                            sheetState.partialExpand()
                        }
                    }
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )




                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = recipe.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.onSurface,
                        maxLines = 2
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = recipe.category,
                        fontSize = 14.sp,
                        color = colors.onSurfaceVariant
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${recipe.minutes}",
                        fontSize = 14.sp,
                        color = colors.onSurface
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
        }
        //
    }

    if(vm.isLoading){
        LoadingOverlay()
    }

    if(vm.showSheet){
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    vm.showSheet = false
                    sheetState.hide()
                }
            },
            sheetState = sheetState,
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 40.dp)
            ){
                AsyncImage(
                    model = vm.generatedRecipe?.imageUrl,
                    contentDescription = vm.generatedRecipe?.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(240.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = vm.generatedRecipe?.title ?: "Sin titulo",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = colors.onSurface
                    )

                    Spacer(Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(colors.primary.copy(alpha = 0.15f))
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = colors.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "${vm.generatedRecipe?.stars}",
                                color = colors.primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = colors.primary,
                                modifier = Modifier.size(16.dp)
                            )

                            Text(
                                text = "${vm.generatedRecipe?.minutes} min",
                                color = colors.primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                text = vm.generatedRecipe?.category ?: "",
                                color = colors.primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = "Ingredientes",
                        fontSize = 18.sp,
                        color = colors.onSurface,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    val ingredients = vm.generatedRecipe?.ingredients ?: listOf()

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ingredients.forEachIndexed { index, ingredient ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(colors.primary.copy(alpha = 0.08f))
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = ingredient,
                                    modifier = Modifier.weight(1f),
                                    color = colors.primary,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = "Preparación",
                        fontSize = 18.sp,
                        color = colors.onSurface,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    val instructions = vm.generatedRecipe?.instructions ?: listOf()

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        instructions.forEachIndexed { index, instruction ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.Top
                            ) {

                                    Text(
                                        text = "${index + 1}",
                                        color = colors.primary,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                Text(
                                    text = instruction,
                                    modifier = Modifier.weight(1f),
                                    color = colors.onSurface,
                                    fontSize = 14.sp,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                            }
                            vm.saveRecipeInDb()
                        }
                    ){
                       Text("Guardar")
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(28.dp))
                            .background(colors.primary)
                            .clickable {
                                scope.launch {
                                    vm.showSheet = false
                                    sheetState.hide()
                                }
                            }
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cerrar",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }



                }
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview(){
    RecipeTheme {
        HomeScreen(
            navController = rememberNavController()
        )
    }
}
