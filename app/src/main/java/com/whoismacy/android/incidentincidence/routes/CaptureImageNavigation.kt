package com.whoismacy.android.incidentincidence.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whoismacy.android.incidentincidence.screens.CaptureImage
import kotlinx.serialization.Serializable

@Serializable
data object CaptureImageRoute

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.captureImageDestination() {
    composable<CaptureImageRoute> {
        CaptureImage()
    }
}

fun NavController.navigateToCaptureImage() {
    navigate(CaptureImageRoute)
}
