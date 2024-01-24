package com.example.profilecreation.ui

import androidx.lifecycle.ViewModel
import com.example.profilecreation.data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun setProfileValues(name: String, email: String, password: String, website: String) {
        _uiState.update { currentState ->
            currentState.copy(
                name = name,
                email = email,
                password = password,
                website = website
            )
        }
    }
}