package com.example.recipeapp.presentation.ui.recipe.fragments

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot.Companion.current
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.ui.recipe.fragments.RecipeListEvent.*
import com.example.recipeapp.repository.RecipeRepository
import com.example.recipeapp.util.TAG
import dagger.assisted.Assisted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

class RecipeListViewModel
@ViewModelInject
constructor(
        private val random: String,
        private val repository: RecipeRepository,
        @Named("auth_token") private val token: String,
        @androidx.hilt.Assisted private val savedStateHandle: SavedStateHandle,
): ViewModel(){
  val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())

  val query = mutableStateOf("")

  val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

  var categoryScrollPosition: Float = 0f

  val loading = mutableStateOf(false)

  val page = mutableStateOf(1)

  private var recipeLisScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            setListScrollPosition(p)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }

        if(recipeLisScrollPosition != 0){
            onTriggeredEvent(RestoreStateEvent)
        }
        else{
            onTriggeredEvent(NewSearchEvent)
        }

    }

    fun onTriggeredEvent(event: RecipeListEvent){
        viewModelScope.launch {
            try{
                when(event){
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                    is RestoreStateEvent -> {
                        restoreState()
                    }
                }
            } catch (e: Exception){
                Log.e(TAG, "onTriggerEvent: Exception: ${e}, ${e.cause}")
            }
        }
    }

    private suspend fun restoreState(){
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()
        for(p in 1..page.value){
            val result = repository.search(
                    token = token,
                    page = p,
                    query = query.value
            )
            results.addAll(result)
            if(p == page.value){ // done
                recipes.value = results
                loading.value = false
            }
        }
    }

   private suspend fun newSearch(){

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

    private suspend fun nextPage(){

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
        setListScrollPosition(position = position)
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
        setQuery(query)
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


    private fun setListScrollPosition(position: Int){
        recipeLisScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int){
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?){
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String){
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}