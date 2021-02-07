package com.example.recipeapp.presentation.ui.recipe.fragments

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot.Companion.current
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.repository.RecipeRepository
import com.example.recipeapp.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

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

  val page = mutableStateOf(1)

  private var recipeLisScrollPosition = 0

    init {
        newSearch()
    }

    fun newSearch(){
        viewModelScope.launch {
            loading.value = true

            resetSearchState()

            delay(3000)
            val result =  repository.search(
                token = token,
                page = 1,
                query = query.value,
            )
            recipes.value = result
            loading.value = false
        }
    }

    fun nextPage(){
        viewModelScope.launch {
            //prevent duplicate events
            if((recipeLisScrollPosition + 1) >= (page.value * PAGE_SIZE)){
                loading.value = true
                incrementPage()
                Log.d(TAG,"nextPage: triggered: ${page.value}")

                // fake delay
                delay(1000)

                if(page.value > 1){
                    val result = repository.search(
                            token = token,
                            page = page.value,
                            query = query.value
                    )
                    Log.d(TAG, "nextPage: ${result}")
                    appendRecipes(result)
                }
                loading.value = false
            }
        }
    }

    // Append new receipes

    private fun appendRecipes(recipes: List<Recipe>){
        val currentList = ArrayList(this.recipes.value)
        currentList.addAll(recipes)
        this.recipes.value = currentList
    }

    private fun incrementPage(){
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int){
        recipeLisScrollPosition = position
    }

    private fun resetSearchState(){
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
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