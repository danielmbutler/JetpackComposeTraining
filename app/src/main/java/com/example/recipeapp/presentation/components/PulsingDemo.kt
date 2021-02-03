package com.example.recipeapp.presentation.components

import android.graphics.Canvas
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.example.recipeapp.presentation.components.PulseAnimationDefinitions.pulseDefinition
import com.example.recipeapp.presentation.components.PulseAnimationDefinitions.pulsePropkey
import com.example.recipeapp.util.TAG


@Composable
fun PulsingDemo(){
    val color = MaterialTheme.colors.primary

    val pulseAnim = transition(
        definition = pulseDefinition,
        initState = PulseAnimationDefinitions.PulseState.INITIAL,
        toState = PulseAnimationDefinitions.PulseState.FINAL,
    )

    val pulseMagnitude = pulseAnim[pulsePropkey]
    Log.d(TAG, "PulsingDemo: $pulseMagnitude")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(pulseMagnitude.dp)
                .width(pulseMagnitude.dp),
            imageVector = Icons.Default.Favorite
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),

        ){
        drawCircle(
            radius = pulseMagnitude,
            brush = SolidColor(color)
        )
    }
}

object PulseAnimationDefinitions{

    enum class PulseState{
        INITIAL, FINAL
    }

    val pulsePropkey = FloatPropKey("pulseKey")

    val pulseDefinition = transitionDefinition<PulseState> {
        state(PulseState.INITIAL){ this[pulsePropkey] = 40f }
        state(PulseState.FINAL){ this[pulsePropkey] = 50f }

        transition(
            PulseState.INITIAL to PulseState.FINAL
        ){
            pulsePropkey using infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                ) ,
                repeatMode = RepeatMode.Restart

            )
        }
    }
}