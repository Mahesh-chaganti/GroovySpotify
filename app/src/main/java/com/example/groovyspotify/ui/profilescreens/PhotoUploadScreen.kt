package com.example.groovyspotify.ui.profilescreens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.groovyspotify.R
import font.helveticaFamily
import kotlinx.coroutines.launch

@Composable
fun PhotoUploadScreen(firestoreViewModel: FirestoreViewModel?,navController: NavController) {
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var downloadUrlFlow = firestoreViewModel?.downloadUrlFlow?.collectAsState()

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(18.dp)
                .size(24.dp)
                .clickable { navController.popBackStack() },
            painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
            contentDescription = "Back button",
            tint = Color(0xFFFF5722)
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 60.dp, top = 8.dp),
            text = "Upload a photo",
            fontSize = 32.sp,
            color = Color.White,
            fontFamily = helveticaFamily,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraBold
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomCenter)
            .padding(horizontal = 40.dp, vertical = 40.dp)) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    modifier = Modifier.size(200.dp),
                    contentDescription = "Photo",
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        onClick = {
                           navController.popBackStack()

                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                        modifier = Modifier
                            .wrapContentSize(),

                        shape = RoundedCornerShape(24.dp),
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 18.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Button(
                        onClick = {



                            scope.launch {

                                firestoreViewModel?.uploadImageToStorage(
                                    imageUri = selectedImageUri!!,
                                    fileName = firestoreViewModel?.myUsername + ": ProfilePhoto",
                                )


                            }


                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                        modifier = Modifier
                            .wrapContentSize(),
                        shape = RoundedCornerShape(24.dp),
                    ) {
                        Text(
                            text = "Upload",
                            fontSize = 18.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Button(
                    onClick = {
                        scope.launch {

                            if (downloadUrlFlow?.value != null) {
                                Log.d("DownloadUrl", "PhotoUploadScreen: ${downloadUrlFlow.value}")

                                val mapData = mapOf(
                                    "profilePhotoUrl" to downloadUrlFlow.value
                                )
                                firestoreViewModel?.updateUserProfile(
                                    userName = firestoreViewModel.myUsername!!,
                                    mapData = mapData
                                )

                            }
                        }
                        navController.navigate("MainHomeScreen")

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                    modifier = Modifier
                        .fillMaxWidth(),

                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text(
                        text = "Finish",
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium
                    )
                }

            }
            else {
                Button(
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
//                        navController?.navigate("ProfileScreenLanguage")

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Pick a photo",
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PhotoUploadScreenPreview() {
    PhotoUploadScreen(firestoreViewModel = null,navController = rememberNavController())
}

