package com.example.cookable.ui.feature.imageprocessing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cookable.ui.scan.ScanViewModel
import com.example.cookable.ui.theme.Background
import com.example.cookable.ui.theme.PrimaryGreen
import kotlinx.coroutines.delay

@Composable
fun ImageProcessingScreen(
    scanViewModel: ScanViewModel,
    onFinished: () -> Unit,
) {
    val photoUri by scanViewModel.photoUri.collectAsState()

    LaunchedEffect(photoUri) {
        if (photoUri != null) {
            delay(1500)
            onFinished()
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            color = PrimaryGreen,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Recognizing ingredients",
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
