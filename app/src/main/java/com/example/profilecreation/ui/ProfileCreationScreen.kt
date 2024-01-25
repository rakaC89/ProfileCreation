package com.example.profilecreation.ui

import android.net.Uri
import android.os.StrictMode
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import coil.compose.rememberAsyncImagePainter
import com.example.profilecreation.R
import com.example.profilecreation.ui.components.Util
import java.util.Objects


@Composable
fun ProfileCreationScreen(
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: (String, String, String, String, Uri) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
            .padding(dimensionResource(id = R.dimen.padding_large))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top title rows
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = TextStyle(
                            fontSize = 40.sp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Text(
                        text = stringResource(id = R.string.app_desc),
                        style = TextStyle(
                            fontSize = 16.sp
                        ),
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                //Camera row
                var imageCaptureState by remember { mutableStateOf(false) }
                val context = LocalContext.current
                val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission().apply{}){}
                val file = Util().createImageFile(context)
                try {
                    val m =
                        StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                    m.invoke(null)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val imgUri by remember{mutableStateOf(
                    FileProvider.getUriForFile(
                        Objects.requireNonNull(context),
                        context.packageName + ".provider", file)
                )}
                val captureLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()){
                    Toast.makeText(context, "Image capture: ${if(it) "Successful" else "Failed"}", Toast.LENGTH_SHORT)
                        .show()
                    imageCaptureState = it
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PERMISSION_GRANTED)
                                permissionLauncher.launch(android.Manifest.permission.CAMERA)
                            else captureLauncher.launch(imgUri)
                        },
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(Color.LightGray),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .widthIn(50.dp)
                                .heightIn(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (!imageCaptureState) {
                                Text(
                                    text = stringResource(id = R.string.tap_to_add_avatar),
                                    color = Color.Gray
                                )
                            } else {
                                Image(
                                    painter = rememberAsyncImagePainter(imgUri),
                                    contentDescription = null,
                                    modifier.size(150.dp),
                                    contentScale = ContentScale.Fit)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                // Text fields row
                var name by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var website by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name,
                        onValueChange = { newText ->
                            name = newText
                        },
                        label = {
                            Text(text = stringResource(R.string.first_name))
                        },
                        placeholder = {
                            Text(text = stringResource(R.string.first_name))
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Gray,
                            unfocusedTextColor = Color.Gray
                        )
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = { newText ->
                            email = newText
                        },
                        label = {
                            Text(text = stringResource(R.string.email_address))
                        },
                        placeholder = {
                            Text(text = stringResource(R.string.email_address))
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Gray,
                            unfocusedTextColor = Color.Gray
                        )
                    )
                    var passwordVisible by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password,
                        onValueChange = { newText ->
                            password = newText
                        },
                        label = {
                            Text(text = stringResource(R.string.password))
                        },
                        placeholder = {
                            Text(text = stringResource(R.string.password))
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Gray,
                            unfocusedTextColor = Color.Gray
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Face
                            else Icons.Filled.Lock

                            IconButton(onClick = {
                                passwordVisible = !passwordVisible
                            }) {
                                Icon(imageVector = image, contentDescription = "")
                            }
                        },
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = website,
                        onValueChange = { newText ->
                            website = newText
                        },
                        label = {
                            Text(text = stringResource(R.string.website))
                        },
                        placeholder = {
                            Text(text = stringResource(R.string.website))
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Gray,
                            unfocusedTextColor = Color.Gray
                        )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onSubmitButtonClicked(name, email, password, website, imgUri) },
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(R.string.submit))
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileCreationScreenPreview() {
    ProfileCreationScreen(
        onSubmitButtonClicked = { s, s1, s2, s3, uri -> })
}