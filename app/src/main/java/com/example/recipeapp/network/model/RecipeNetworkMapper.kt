package com.example.recipeapp.network.model

import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.util.EntityMapper

class RecipeNetworkMapper : EntityMapper<RecipeNetworkEntity, Recipe> {
    override fun mapFromEntity(entity: RecipeNetworkEntity): Recipe {
        return Recipe(
            id = entity.pk,
            title = entity.title,
            featuredImage = entity.featuredImage,
            rating = entity.rating,
            publisher = entity.publisher,
            sourceUrl = entity.sourceurl,
            description = entity.description,
            cookingInstructions = entity.cooking_instructions,
            ingredients = entity.ingredients?: listOf(),
            dateAdded = entity.date_added,
            dateUpdated = entity.date_updated


        )
    }

    override fun mapToEntity(domainModel: Recipe): RecipeNetworkEntity {
        return RecipeNetworkEntity(
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

    fun fromEntityList(initial: List<RecipeNetworkEntity>): List<Recipe>{
        return initial.map { mapFromEntity(it) }
    }

    fun toEntityList(initial: List<Recipe>) : List<RecipeNetworkEntity>{
        return initial.map{mapToEntity(it)}
    }

}