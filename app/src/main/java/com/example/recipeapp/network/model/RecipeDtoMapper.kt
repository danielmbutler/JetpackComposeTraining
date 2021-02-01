package com.example.recipeapp.network.model

import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
            id = model.pk,
            title = model.title,
            featuredImage = model.featuredImage,
            rating = model.rating,
            publisher = model.publisher,
            sourceUrl = model.sourceurl,
            description = model.description,
            cookingInstructions = model.cooking_instructions,
            ingredients = model.ingredients?: listOf(),
            dateAdded = model.date_added,
            dateUpdated = model.date_updated


        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            pk = domainModel.id,
            title = domainModel.title,
            featuredImage = domainModel.featuredImage,
            rating = domainModel.rating,
            publisher = domainModel.publisher,
            sourceurl = domainModel.sourceUrl,
            description = domainModel.description,
            cooking_instructions = domainModel.cookingInstructions,
            ingredients = domainModel.ingredients?: listOf(),
            date_added = domainModel.dateAdded,
            date_updated = domainModel.dateUpdated,


        )
    }

    fun ToDomainList(initial: List<RecipeDto>): List<Recipe>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>) : List<RecipeDto>{
        return initial.map{mapFromDomainModel(it)}
    }

}