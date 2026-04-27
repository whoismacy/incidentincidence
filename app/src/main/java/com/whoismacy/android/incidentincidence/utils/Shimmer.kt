package com.whoismacy.android.incidentincidence.utils

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.shimmer(
    cornerRadius: Dp = 0.dp,
): Modifier {
    val shimmerColours =
        listOf(
            Color.LightGray.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.3f),
        )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnimation by transition.animateFloat(
        initialValue = -4000f,
        targetValue = 1200f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(
                        durationMillis = 1600,
                        easing = FastOutSlowInEasing,
                    ),
            ),
        label = "translate",
    )

    return this.drawWithCache {
        val brush =
            Brush.linearGradient(
                colors = shimmerColours,
                start = Offset(translateAnimation, 0f),
                end = Offset(translateAnimation + size.width / 1.5f, size.height),
            )
        val cornerPx = cornerRadius.toPx()
        onDrawWithContent {
            drawRoundRect(
                brush = brush,
                cornerRadius = CornerRadius(cornerPx, cornerPx),
                size = size,
            )
        }
    }
}

@Composable
fun Modifier.shimmer(
    cornerRadius: Dp = 0.dp,
    isLoading: Boolean,
) = if (isLoading) this.shimmer(cornerRadius = cornerRadius) else this
