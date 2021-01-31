package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.example.recipeapp.network.model.RecipeService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val service = Retrofit.Builder()
           .baseUrl("https://food2fork.ca/api/recipe/")
           .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
           .build()
           .create(RecipeService::class.java)

       CoroutineScope(IO).launch {
           val response = service.get(
               token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
               id = 583
           )
           Log.d("MainActivity", "onCreate: ${response.title}")
       }
    }
}
