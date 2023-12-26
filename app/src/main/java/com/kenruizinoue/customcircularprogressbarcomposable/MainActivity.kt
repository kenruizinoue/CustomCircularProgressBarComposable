package com.kenruizinoue.customcircularprogressbarcomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Remembering the Progress State
            val progressFlow = remember { progressFlow(delayTime = 10L) }
            val progressState = progressFlow.collectAsState(initial = 0f)
            Column {
                Box (
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    CustomerCircularProgressBar(
                        progress = progressState.value,
                        progressArcColor1 = Color(0xFF673AB7),
                        progressArcColor2 = Color(0xFF4CAF50)
                    )
                }
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    CustomerCircularProgressBar(
                        progress = 0.75f,
                        startAngle = 180f,
                        size = 160.dp,
                        strokeWidth = 24.dp,
                        progressArcColor1 = Color(0xFFECBC13),
                        backgroundArcColor = Color(0xFF1343EC),
                        animationOn = true
                    )
                }
            }
        }
    }
}