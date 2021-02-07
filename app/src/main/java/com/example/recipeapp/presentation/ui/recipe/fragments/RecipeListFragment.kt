package com.example.recipeapp.presentation.ui.recipe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.recipeapp.R
import com.example.recipeapp.presentation.BaseApplication
import com.example.recipeapp.presentation.components.*
import com.example.recipeapp.presentation.components.util.snackbarController
import com.example.recipeapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController = snackbarController(lifecycleScope)

    private val viewModel: RecipeListViewModel by viewModels()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        println("fragment: ${viewModel.getRandomString()}")
//        println("fragment: ${viewModel.getToken()}")
//        println("fragment: ${viewModel.getRepo()}")
//    }


    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return ComposeView(requireContext()).apply {

            setContent {
                AppTheme(
                    darkTheme = application.isDark.value,
                ) {
                    val recipes = viewModel.recipes.value

                    val query = viewModel.query.value

                    val selectedCategory = viewModel.selectedCategory.value
                    val categoryScrollPosition = viewModel.categoryScrollPosition

                    val loading = viewModel.loading.value

                    val page = viewModel.page.value

                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                            topBar = {
                                SearchAppBar(
                                        query = query,
                                        onQueryChanged = viewModel::onQueryChanged,
                                        onExecuteSearch = {
                                            if (viewModel.selectedCategory.value?.value == "Milk"){
                                                snackbarController.getScope().launch {
                                                    snackbarController.showSnackbar(
                                                            scaffoldState = scaffoldState,
                                                            message = "Invalid category: MILK",
                                                            actionLable = "Hide"
                                                    )
                                                }
                                            }
                                            else{
                                                viewModel.onTriggeredEvent(RecipeListEvent.NewSearchEvent)
                                            }
                                        },
                                        categories = getAllFoodCategories(),
                                        selectedCategory = selectedCategory,
                                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                        scrollPosition = categoryScrollPosition,
                                        onChangeScrollPosition = viewModel::onChangeCategoryScrollPostions,
                                        onToggleTheme = application::toggleLightTheme
                                )
                            },
                            scaffoldState = scaffoldState,
                            snackbarHost = {
                                scaffoldState.snackbarHostState
                            }

                    ) {
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
                                        viewModel
                                                .onChangeRecipeScrollPosition(index)
                                        if((index + 1) >=(page * PAGE_SIZE) && !loading){
                                            viewModel.onTriggeredEvent(RecipeListEvent.NextPageEvent)
                                        }
                                        RecipeCard(recipe = recipe, onClick = {})

                                    }
                                }


                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading) //highest priority
                            DefaultSnackbar(
                                    snackbarHostState = scaffoldState.snackbarHostState,
                                    onDismiss = {
                                        scaffoldState.snackbarHostState
                                                .currentSnackbarData?.dismiss()
                                    },
                                    modifier = Modifier.align(Alignment.BottomCenter)
                                    )
                        }
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

@Composable
fun MyBottomBar(
   //navigationController: NavController
){
    BottomNavigation(
        elevation = 12.dp,
    ){
        BottomNavigationItem(
            icon = { Icon(Icons.Default.BrokenImage) },
            selected = false,
            onClick = {  }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Search) },
            selected = true,
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.AccountBalanceWallet) },
            selected = false,
            onClick = { /*TODO*/ }
        )
    }

}

@Composable
fun myDraw(){
    Column() {
        Text("item1")
        Text("item1")
        Text("item1")
        Text("item1")
        Text("item1")

    }
}

