package com.example.profilecreation.data

import android.net.Uri

data class ProfileUiState (
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val website: String = "",
    val imgUri: Uri? = null
)