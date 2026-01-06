package com.example.cookable.ui.scan

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ScanViewModel : ViewModel() {
    private val _photoUri = MutableStateFlow<Uri?>(null)
    val photoUri = _photoUri.asStateFlow()

    fun onPhotoCaptured(uri: Uri) {
        _photoUri.value = uri
    }

    fun clearPhoto() {
        _photoUri.value = null
    }
}
