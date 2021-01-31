package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.fragments.RecipeListFragment
import com.example.recipeapp.network.model.RecipeNetworkEntity
import com.example.recipeapp.network.model.RecipeNetworkMapper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapper = RecipeNetworkMapper()

        val recipe = Recipe()

        val networkEntity: RecipeNetworkEntity = mapper.mapToEntity(recipe)

        val r: Recipe  = mapper.mapFromEntity(networkEntity)

    }
}
