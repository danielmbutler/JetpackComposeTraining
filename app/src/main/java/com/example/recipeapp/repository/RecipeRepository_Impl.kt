package com.example.recipeapp.repository

import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.network.model.RecipeDtoMapper
import com.example.recipeapp.network.model.RecipeService

class RecipeRepository_Impl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
): RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.ToDomainList(recipeService.search(token, page, query).recipes)
    }

    override suspend fun get(token: String, id: Int): Recipe{
        return mapper.mapToDomainModel(recipeService.get(token,id))
    }
}