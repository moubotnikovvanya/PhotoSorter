package com.example.photosorter

import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel

class FragmentCommunicator:ViewModel() {
    var uri:Uri? = null
    var images: List<DocumentFile>? = null
    var position: Int? = null
}