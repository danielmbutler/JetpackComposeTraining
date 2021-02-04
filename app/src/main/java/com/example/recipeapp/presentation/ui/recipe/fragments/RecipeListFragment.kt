package com.example.recipeapp.presentation.ui.recipe.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.presentation.AnimationHeartButton
import com.example.recipeapp.presentation.HeartAnimationDefinition
import com.example.recipeapp.presentation.HeartAnimationDefinition.HeartButtonState.*
import com.example.recipeapp.presentation.components.*
import com.example.recipeapp.util.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        println("fragment: ${viewModel.getRandomString()}")
//        println("fragment: ${viewModel.getToken()}")
//        println("fragment: ${viewModel.getRepo()}")
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value

                val query = viewModel.query.value

                val selectedCategory = viewModel.selectedCategory.value

                val loading = viewModel.loading.value

                Column {
                    SearchAppBar(
                        query = query,
                        onQueryChanged =  viewModel::onQueryChanged,
                        onExecuteSearch = viewModel::newSearch,
                        ScrollPostion = viewModel.categoryScrollPosition,
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        onChangeCategoryScrollPostions = viewModel::onChangeCategoryScrollPostions
                    )

                    //LoadingRecipeListShimmer(imageHeight = 250.dp)


                    Box(  // overlays on top of children
                        modifier = Modifier.fillMaxSize()
                    ){
                        if(loading){
                            LoadingRecipeListShimmer(imageHeight = 250.dp)
                        }

                        else {
                            LazyColumn {
                                itemsIndexed(
                                    items = recipes
                                ){ index, recipe ->
                                    RecipeCard(recipe = recipe, onClick = {})

                                }
                            }


                        }
                        CircularIndeterminateProgressBar(isDisplayed = loading) //highest priority
                    }

                }
            }
        }
    }
}

@Composable
fun GradientDemo(){
    val colors = listOf(Color.Blue,Color.Red,Color.Blue)
    val brush = linearGradient(
        colors,
        start = Offset(200f,200f),
        end = Offset(400f,400f)
    )
    Surface(shape = MaterialTheme.shapes.small) {
        Spacer(modifier = Modifier
            .fillMaxSize()
            .background(brush = brush))

    }
}