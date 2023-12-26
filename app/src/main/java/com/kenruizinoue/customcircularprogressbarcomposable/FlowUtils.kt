package com.kenruizinoue.customcircularprogressbarcomposable

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

/**
 * A function that creates a Flow emitting Float values, simulating a progress animation.
 *
 * @param targetProgress The final progress value to reach, default is 1f.
 * @param step The increment for each emitted progress value, default is 0.01f.
 * @param delayTime The delay between emissions in milliseconds, default is 1L.
 * @return A Flow emitting Float values representing the progress.
 */
fun progressFlow(targetProgress: Float = 1f, step: Float = 0.01f, delayTime: Long = 1L): Flow<Float> {
    return flow {
        var progress = 0f
        while (progress <= targetProgress) {
            emit(progress)
            progress += step
            delay(delayTime)
        }
    }
}
