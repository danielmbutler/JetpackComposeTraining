package com.example.recipeapp.presentation.components

import androidx.compose.animation.core.*

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