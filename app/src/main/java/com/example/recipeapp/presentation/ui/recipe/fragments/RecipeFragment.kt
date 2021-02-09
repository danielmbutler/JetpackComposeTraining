package com.example.recipeapp.presentation.ui.recipe.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.recipeapp.presentation.BaseApplication
import com.example.recipeapp.presentation.components.*
import com.example.recipeapp.presentation.components.util.snackbarController
import com.example.recipeapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RecipeFragment  : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private  val snackbarController = snackbarController(lifecycleScope)

    private val viewModel: RecipeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))

        }
    }

    @ExperimentalMaterialApi
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value

                val recipe = viewModel.recipe.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(darkTheme = application.isDark.value) {
                    Scaffold(
                            scaffoldState = scaffoldState,
                            snackbarHost = {
                                scaffoldState.snackbarHostState
                            }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()){
                            if(loading && recipe == null){
                                LoadingRecipeShimmer(imageHeight = IMAGE_HEIGHT.dp)
                            } else {
                                recipe?.let{
                                    if(it.id == 1){
                                        snackbarController.showSnackbar(scaffoldState = scaffoldState,
                                        message = "an error occurred",
                                        actionLable = "ok"
                                        )
                                    } else {
                                        RecipeView(recipe = it)
                                    }
                                }
                            }
                        }
                        CircularIndeterminateProgressBar(isDisplayed = loading,)
                        DefaultSnackbar(snackbarHostState = scaffoldState.snackbarHostState,
                                onDismiss = { scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                        )
                    }
                }


            }
        }
    }
}