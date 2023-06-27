package com.example.groovyspotify.ui.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularDotsAnimation() {
    val dotCount = 8
    val dotRadius = 4.dp
    val angle = 360f

    val transition = rememberInfiniteTransition()

    val animatedAngles = (0 until dotCount).map { index ->
        transition.animateFloat(
            initialValue = (index * (angle / dotCount)),
            targetValue = (index * (angle / dotCount)) + angle,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(

        contentAlignment = Alignment.Center
    ) {
        animatedAngles.forEach { animatedAngle ->
            val dotPosition by animatedAngle
            val dotX = cos(Math.toRadians(dotPosition.toDouble())).toFloat()
            val dotY = sin(Math.toRadians(dotPosition.toDouble())).toFloat()
            Dot(Offset(dotX, dotY), dotRadius,Modifier.size(40.dp).align(Alignment.Center))
        }
    }
}

@Composable
fun Dot(position: Offset, radius: Dp,modifier: Modifier) {
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        val dotX = centerX + (position.x * (size.width / 2))
        val dotY = centerY + (position.y * (size.height / 2))

        drawCircle(
            color = Color.White,
            radius = radius.toPx(),
            center = Offset(dotX, dotY)
        )
    }
}




@Preview()
@Composable
fun PreviewDot() {

CircularDotsAnimation()
}