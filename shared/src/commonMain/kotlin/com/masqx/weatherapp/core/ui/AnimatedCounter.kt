package com.masqx.weatherapp.core.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlin.random.Random

/** Ease-out-ish curve: fast start, settles into place — feels like a counter "arriving" rather than ticking. */
private val CounterEasing: Easing = CubicBezierEasing(0.16f, 1f, 0.3f, 1f)

/**
 * Animates an [Int] value from 0 up to [targetValue] with a curved (non-linear) ramp, a randomized
 * duration, and a randomized start delay on first composition — so multiple counters on screen
 * visibly start ticking at slightly different moments instead of all landing on target at once.
 * [content] renders the current animated value — pass a `Text`, a gauge, etc.
 */
@Composable
fun AnimatedCounter(
    targetValue: Int,
    minDurationMillis: Int = 900,
    maxDurationMillis: Int = 1800,
    maxStartDelayMillis: Int = 350,
    content: @Composable (value: Int) -> Unit,
) {
    val animatable = remember { Animatable(0f) }
    val durationMillis = remember { Random.nextInt(minDurationMillis, maxDurationMillis) }
    val startDelayMillis = remember { Random.nextInt(0, maxStartDelayMillis) }

    LaunchedEffect(targetValue) {
        delay(startDelayMillis.toLong())
        animatable.animateTo(
            targetValue = targetValue.toFloat(),
            animationSpec = tween(durationMillis = durationMillis, easing = CounterEasing),
        )
    }

    content(animatable.value.toInt())
}
