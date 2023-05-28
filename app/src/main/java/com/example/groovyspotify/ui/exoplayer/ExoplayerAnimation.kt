package com.example.groovyspotify.ui.exoplayer


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.lerp


import kotlin.random.Random


@Composable
fun WaveformAnimation(
    modifier: Modifier,
    barColor: Color,
    maxLinesCount: Int,
    barWidth: Float,
    gapWidth: Float,
    canvasCenterY: Float,
    isAnimating: Boolean
) {
//    val infiniteTransition = rememberInfiniteTransition()

    val random = remember { Random(System.currentTimeMillis()) }
    val infiniteAnimation = rememberInfiniteTransition()
    val animations = mutableListOf<State<Float>>()
    val isAnimating = remember(isAnimating) { (isAnimating) }

    val heightDivider by animateFloatAsState(
        targetValue = if (isAnimating) 0.5f else 4f,
        animationSpec = tween(1000, easing = LinearEasing)
    )
    val initialMultipliers = remember {
        mutableListOf<Float>().apply {
            repeat(maxLinesCount) { this += random.nextFloat() }
        }
    }
    repeat(15) {
        val durationMillis = random.nextInt(500, 2000)
        animations += infiniteAnimation.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis),
                repeatMode = RepeatMode.Reverse,
            )
        )
    }
//    val amplitudeValues = List(barCount) {
//        val targetAmplitude = (Math.random() * maxAmplitude).toFloat()
//        infiniteTransition.animateFloat(
//            initialValue = 0f,
//            targetValue = targetAmplitude,
//            animationSpec = infiniteRepeatable(
//                animation = keyframes {
//                    durationMillis = animationDurationMillis
//                    targetAmplitude at 0 with LinearOutSlowInEasing
//                    0f at animationDurationMillis / 2 with FastOutSlowInEasing
//                    targetAmplitude at animationDurationMillis with LinearOutSlowInEasing
//                }
//            )
//        )
//    }

    Canvas(
        modifier = modifier
            .height(40.dp)
            .width(200.dp)
    ) {

        val canvasWidth = size.width
        val canvasHeight = size.height
        val count = (canvasWidth / (barWidth + gapWidth)).toInt().coerceAtMost(maxLinesCount)

        val animatedVolumeWidth = count * (barWidth + gapWidth)
        var startOffset = (canvasWidth - animatedVolumeWidth) / 2

        val barMinHeight = 0f
        val barMaxHeight = canvasHeight / 2f / heightDivider






        repeat(count) { index ->
            val currentSize = animations[index % animations.size].value
            var barHeightPercent = initialMultipliers[index] + currentSize
            if (barHeightPercent > 1.0f) {
                val diff = barHeightPercent - 1.0f
                barHeightPercent = 1.0f - diff
            }
            val barHeight =
                lerp(start = barMinHeight, stop = barMaxHeight, fraction = barHeightPercent)
            drawLine(
                color = barColor,
                start = Offset(startOffset, canvasCenterY - barHeight / 2),
                end = Offset(startOffset, canvasCenterY + barHeight / 2),
                strokeWidth = barWidth,
                cap = StrokeCap.Round,
            )
            startOffset += barWidth + gapWidth
        }
//        amplitudeValues.forEachIndexed { index, amplitude ->
//            val barHeight = amplitude.value / maxAmplitude * canvasHeight
//            val barRect = Rect(
//                left = startX + index * (barWidth + barSpacing),
//                top = canvasHeight - barHeight,
//                right = startX + index * (barWidth + barSpacing) + barWidth,
//                bottom = canvasHeight
//            )
//            drawRoundRect(
//                color = waveformColor,
//                topLeft = barRect.topLeft,
//                size = Size(barWidth.dp.toPx(), barHeight),
//                cornerRadius = CornerRadius(5f)
//            )
////            drawRect(rect = barRect, color = waveformColor)
//        }
    }
}


fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (start * (1 - fraction) + stop * fraction)
}

@Composable
fun ExoplayerAnimation(isAnimating: Boolean, modifier: Modifier) {

    WaveformAnimation(
        modifier = modifier,
        barColor = Color.White,
        maxLinesCount = 30,
        barWidth = 12f,
        gapWidth = 8f,
        canvasCenterY = 55f,
        isAnimating = isAnimating
    )

}

@Preview
@Composable
fun AnimationPreview() {
    ExoplayerAnimation(isAnimating = false, modifier = Modifier)
}