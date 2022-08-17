package com.smooth.travelplanner.presentation.camera

import android.Manifest
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination

@ExperimentalPermissionsApi
@Destination
@Composable
fun CameraScreen(
    homeScreenNavController: NavController
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted) {
        CameraView(onImageCaptured = { uri, fromGallery ->
            Log.d("CameraScreen", "Image Uri Captured from Camera View")
            //TODO, process the uri
        }, onError = { e ->
            Log.d("CameraScreen", e.message ?: e.toString())
        })
    } else {
        SideEffect {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}