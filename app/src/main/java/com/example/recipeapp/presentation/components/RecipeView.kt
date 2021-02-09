package com.example.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.util.DEFAULT_RECIPE_IMAGE
import com.example.recipeapp.util.loadPicture

const val IMAGE_HEIGHT = 260

@Composable
fun RecipeView(
        recipe: Recipe,
){
    ScrollableColumn(modifier = Modifier.fillMaxWidth()) {
        recipe.featuredImage?.let{
            val image = loadPicture(url = it, defaultImage = DEFAULT_RECIPE_IMAGE).value
            image?.let {  img ->
                Image(
                        bitmap = img.asImageBitmap(),
                        modifier = Modifier
                                .fillMaxWidth()
                                .preferredHeight(IMAGE_HEIGHT.dp),
                        contentScale = ContentScale.Crop
                )

            }
        }
        Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            recipe.title?.let { title ->
                Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)) {
                    Text(
                            text = title,
                            modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .wrapContentWidth(align = Alignment.Start),
                            style = MaterialTheme.typography.h3
                    )
                    val rank = recipe.rating.toString()
                    Text(
                            text = rank,
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(align = Alignment.End)
                                    .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.h5
                    )
                }
                recipe.publisher?.let { publisher ->
                    val updated = recipe.dateUpdated
                    Text(
                            text = if(updated != null){
                                    "Updated $updated by $publisher"
                            }
                            else {
                                "updated by $publisher"
                            },
                            modifier = Modifier.fillMaxWidth()
                                    .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.caption
                    )
                }

                for(ingredient in recipe.ingredients){
                    Text(
                            text = ingredient,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            style = MaterialTheme.typography.body1
                    )
                }

            }
        }
    }
}