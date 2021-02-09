package com.example.recipeapp.presentation.ui.recipe.fragments



sealed class RecipeEvent{

    data class GetRecipeEvent(
            val id: Int
    ): RecipeEvent()

}