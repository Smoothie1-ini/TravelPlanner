package com.smooth.travelplanner.presentation.camera

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun CameraScreen(
    homeScreenNavController: NavController
) {
    CameraView(onImageCaptured = { uri, fromGallery ->
        Log.d("CameraScreen", "Image Uri Captured from Camera View")
        //TODO, process the uri
    }, onError = { imageCaptureException ->
        Log.d("CameraScreen", "Image Uri Captured from Camera View")
    })
}