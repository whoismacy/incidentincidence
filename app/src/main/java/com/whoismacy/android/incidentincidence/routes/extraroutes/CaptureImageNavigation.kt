package com.whoismacy.android.incidentincidence.routes.extraroutes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whoismacy.android.incidentincidence.screens.CaptureImage
import kotlinx.serialization.Serializable

@Serializable
data class CaptureImageRoute(
    val id: Int,
)

@RequiresApi(Build.VERSION_CODES.Q)
fun NavGraphBuilder.captureImageDestination(onNavigateBack: () -> Unit) {
    composable<CaptureImageRoute> { backStackEntry ->
        val captureImageRoute = backStackEntry.toRoute<CaptureImageRoute>()
        CaptureImage(onNavigateBack = onNavigateBack, id = captureImageRoute.id)
    }
}

fun NavController.navigateToCaptureImage(id: Int) {
    navigate(CaptureImageRoute(id))
}
