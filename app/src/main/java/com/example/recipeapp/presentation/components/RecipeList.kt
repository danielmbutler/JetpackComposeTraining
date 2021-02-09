package com.example.recipeapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipeapp.R
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.components.util.snackbarController
import com.example.recipeapp.presentation.ui.recipe.fragments.PAGE_SIZE
import com.example.recipeapp.presentation.ui.recipe.fragments.RecipeListEvent
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun RecipeList(
        loading:Boolean,
        recipes: List<Recipe>,
        onChangeRecipeScrollPosition: (Int) -> Unit,
        page: Int,
        onTriggeredEvent: (RecipeListEvent) -> Unit,
        scaffoldState: ScaffoldState,
        snackbarController: snackbarController,
        navController: NavController,
){
    Box(  // overlays on top of children
            modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colors.surface)
    ){
        if(loading && recipes.isEmpty()){
            LoadingRecipeListShimmer(imageHeight = 250.dp)
        }

        else {
            LazyColumn {
                itemsIndexed(
                        items = recipes
                ){ index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    if((index + 1) >=(page * PAGE_SIZE) && !loading){
                        onTriggeredEvent(RecipeListEvent.NextPageEvent)
                    }
                    RecipeCard(recipe = recipe, onClick = {
                        if(recipe.id != null){
                            val bundle = Bundle()
                            bundle.putInt("recipeId", recipe.id)
                            navController.navigate(R.id.viewRecipe, bundle)
                        }else{
                            snackbarController.getScope().launch {
                                snackbarController.showSnackbar(
                                        scaffoldState = scaffoldState,
                                        message = "recipe Error",
                                        actionLable = "OK"
                                )
                            }
                        }
                    })

                }
            }
        }
    }
}

