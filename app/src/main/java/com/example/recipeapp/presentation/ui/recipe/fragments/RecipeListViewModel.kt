package com.example.recipeapp.presentation.ui.recipe.fragments

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

class RecipeListViewModel
@ViewModelInject
constructor(
    private val random: String,
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
): ViewModel(){
  val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())

  val query = mutableStateOf("")

  val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

  var categoryScrollPosition: Float = 0f

  val loading = mutableStateOf(false)

    init {
        newSearch()
    }

    fun newSearch(){
        viewModelScope.launch {
            loading.value = true

            resetSearchState()

            delay(2000)
            val result =  repository.search(
                token = token,
                page = 1,
                query = query.value,
            )
            recipes.value = result
            loading.value = false
        }
    }

    private fun resetSearchState(){
        recipes.value = listOf()
        if(selectedCategory.value?.value != query.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String){
        this.query.value = query
        println("onquerychanged: $query")
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
        println("onselectedCategoryChanged: $category")
    }

    fun onChangeCategoryScrollPostions(position: Float){
        categoryScrollPosition = position
    }
}