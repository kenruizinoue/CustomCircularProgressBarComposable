package com.kenruizinoue.customcircularprogressbarcomposable

// Needed imports for all the steps
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Composable function to draw a custom circular progress UI.
 * @param progress The current progress value, where 0f represents no progress and 1f represents complete progress.
 * @param startAngle The starting angle for the progress arc, in degrees. 0 degrees is at the 3 o'clock position.
 * @param size The diameter of the circular progress UI.
 * @param strokeWidth The thickness of the progress stroke.
 * @param backgroundArcColor The color of the background arc that shows the full extent of the progress circle.
 * @param progressArcColor1 The starting color of the progress arc's gradient.
 * @param progressArcColor2 The ending color of the progress arc's gradient. If not specified, it defaults to the same color as progressArcColor1, resulting in a solid color.
 */
@Composable
fun CustomerCircularProgressBar(
    progress: Float = 0f,
    startAngle: Float = 270f,
    size: Dp = 96.dp,
    strokeWidth: Dp = 12.dp,
    backgroundArcColor: Color = Color.LightGray,
    progressArcColor1: Color = Color.Blue,
    progressArcColor2: Color = progressArcColor1,
    animationOn: Boolean = false,
    animationDuration: Int = 1000
) {
    // Progress Animation Implementation
    val currentProgress = remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = currentProgress.floatValue,
        animationSpec = if (animationOn) tween(animationDuration) else tween(0),
        label = "Progress Animation"
    )
    LaunchedEffect(animationOn, progress) {
        if (animationOn) {
            progressFlow(progress).collect { value ->
                currentProgress.floatValue = value
            }
        } else {
            currentProgress.floatValue = progress
        }
    }
    Canvas(modifier = Modifier.size(size)) {
        val strokeWidthPx = strokeWidth.toPx()
        // Correct Arc Size
        val arcSize = size.toPx() - strokeWidthPx
        // Background Arc Implementation
        drawArc(
            color = backgroundArcColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            // Offset Half Stroke Width
            topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
            size = Size(arcSize, arcSize),
            style = Stroke(width = strokeWidthPx)
        )
        // Gradient Brush
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(progressArcColor1, progressArcColor2, progressArcColor1)
        )
        // Progress Arc Implementation
        withTransform({
            rotate(degrees = startAngle, pivot = center)
        }) {
            drawArc(
                brush = gradientBrush,
                startAngle = 0f,
                sweepAngle = animatedProgress * 360,
                useCenter = false,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )
        }
    }
}

@Preview
@Composable
fun CustomerCircularProgressBarPreview() {
    CustomerCircularProgressBar(
        progress = 0.85f,
        progressArcColor1 = Color(0xFF673AB7),
        progressArcColor2 = Color(0xFF4CAF50),
    )
}
