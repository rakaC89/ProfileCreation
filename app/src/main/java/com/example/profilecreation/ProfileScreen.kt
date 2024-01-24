package com.example.profilecreation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.profilecreation.ui.ProfileCreationScreen
import com.example.profilecreation.ui.ProfileSummaryScreen
import com.example.profilecreation.ui.ProfileViewModel

enum class ProfileScreen(val title: String) {
    Creation(title = "Creation"),
    Summary(title = "Summary")
}

@Composable
fun ProfileApp(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    startDestination: String = ProfileScreen.Creation.title
) {
    val uiState by viewModel.uiState.collectAsState()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ProfileScreen.Creation.title) {
            ProfileCreationScreen(
                onSubmitButtonClicked = { name: String, email: String, password: String, website: String ->
                    viewModel.setProfileValues(name, email, password, website)
                    navController.navigate(ProfileScreen.Summary.title) }
            )
        }
        composable(ProfileScreen.Summary.title) {
            ProfileSummaryScreen(
                profileUiState = uiState,
                onSignInButtonClicked = { navController.navigate(ProfileScreen.Creation.title) }
            )
        }
    }
}