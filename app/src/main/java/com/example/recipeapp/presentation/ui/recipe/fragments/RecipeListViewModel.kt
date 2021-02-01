package com.example.recipeapp.presentation.ui.recipe.fragments

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class RecipeListViewModel
@ViewModelInject
constructor(
    private val random: String
): ViewModel(){
    init{
        println("viewmodel: $random")
    }

}