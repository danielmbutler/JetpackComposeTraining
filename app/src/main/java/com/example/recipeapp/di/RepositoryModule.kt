package com.example.recipeapp.di

import com.example.recipeapp.network.model.RecipeDtoMapper
import com.example.recipeapp.network.model.RecipeService
import com.example.recipeapp.repository.RecipeRepository
import com.example.recipeapp.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providesRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ):RecipeRepository{
        return RecipeRepository_Impl(recipeService, recipeDtoMapper,)
    }
}