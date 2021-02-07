package com.example.recipeapp.presentation.ui.recipe.fragments

sealed class RecipeListEvent {
    object NewSearchEvent : RecipeListEvent()

    object  NextPageEvent: RecipeListEvent()
}