package com.example.cookable.ui.feature.scan

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.cookable.ui.scan.CameraPreview
import com.example.cookable.ui.scan.ScanViewModel
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.Primary
import java.io.File

@Composable
fun ScanScreen(
    onScan: () -> Unit,
    onCancel: () -> Unit,
    scanViewModel: ScanViewModel,
) {
    val context = LocalContext.current

    val imageCapture =
        remember {
            ImageCapture.Builder().build()
        }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { granted ->
            hasCameraPermission = granted
        }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White),
    ) {
        if (hasCameraPermission) {
            CameraPreview(
                imageCapture = imageCapture,
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
            )
        } else {
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Camera permission required")
            }
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier =
                    Modifier
                        .weight(1f)
                        .height(52.dp),
                shape = RoundedCornerShape(18.dp),
                colors =
                    ButtonDefaults.outlinedButtonColors(
                        contentColor = Primary,
                    ),
                border = BorderStroke(1.dp, Line),
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Button(
                onClick = {
                    takePhoto(
                        context = context,
                        imageCapture = imageCapture,
                    ) { uri ->
                        scanViewModel.onPhotoCaptured(uri)
                        onScan()
                    }
                },
                modifier =
                    Modifier
                        .weight(1f)
                        .height(52.dp),
                shape = RoundedCornerShape(18.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.White,
                    ),
                elevation =
                    ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                    ),
            ) {
                Text(
                    text = "Scan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}

fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onPhotoSaved: (Uri) -> Unit,
) {
    val photoFile =
        File(
            context.cacheDir,
            "scan_${System.currentTimeMillis()}.jpg",
        )

    val outputOptions =
        ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onPhotoSaved(Uri.fromFile(photoFile))
            }

            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
            }
        },
    )
}
