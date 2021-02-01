package com.example.recipeapp.presentation.ui.recipe.fragments

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.recipeapp.repository.RecipeRepository
import javax.inject.Named

class RecipeListViewModel
@ViewModelInject
constructor(
    private val random: String,
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
): ViewModel(){
    init{
        println("viewmodel: $random")
        println("viewmodel: $repository")
        println("viewmodel: $token")
    }

}