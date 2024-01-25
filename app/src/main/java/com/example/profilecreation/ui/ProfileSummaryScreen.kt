package com.example.profilecreation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.profilecreation.R
import com.example.profilecreation.data.ProfileUiState

@Composable
fun ProfileSummaryScreen(
    modifier: Modifier = Modifier,
    profileUiState: ProfileUiState,
    onSignInButtonClicked: () -> Unit = {}
) {
    val helloString = stringResource(id = R.string.hello) + profileUiState.name
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(dimensionResource(id = R.dimen.padding_large))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                ) {
                    Text(
                        text = helloString,
                        style = TextStyle(
                            fontSize = 40.sp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = stringResource(id = R.string.summary_desc),
                        style = TextStyle(
                            fontSize = 16.sp
                        ),
                        color = Color.DarkGray
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(profileUiState.imgUri),
                        contentDescription = null,
                        modifier.size(300.dp),
                        contentScale = ContentScale.Fit)
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))

                ) {
                    Text(text = profileUiState.website,
                        color = Color.DarkGray)
                    Text(text = profileUiState.name,
                        color = Color.DarkGray)
                    Text(text = profileUiState.email,
                        color = Color.DarkGray)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onSignInButtonClicked,
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(R.string.sign_in))
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileSummaryPreview() {
    ProfileSummaryScreen(
        profileUiState = ProfileUiState(
            "Raka",
            "abc@efg.com",
            "",
            "www.google.com"
        )
    )
}
