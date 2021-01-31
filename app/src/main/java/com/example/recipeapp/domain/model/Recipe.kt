package com.example.recipeapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val cooking_instructions: String,
    val date_added: String,
    val date_updated: String,
    val description: String,
    val featured_image: String,
    val id: Int,
    val ingredients: List<String>,
    val long_date_added: Int,
    val long_date_updated: Int,
    val publisher: String,
    val rating: Int,
    val source_url: String,
    val title: String
) : Parcelable