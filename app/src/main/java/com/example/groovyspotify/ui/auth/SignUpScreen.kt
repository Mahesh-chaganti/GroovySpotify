package com.example.groovyspotify.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.groovyspotify.R
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.ui.home.CircularDotsAnimation
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import font.helveticaFamily
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(viewModel: AuthViewModel?,firestoreViewModel: FirestoreViewModel?, navController: NavController?) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var signupFlow = viewModel?.signupFlow?.collectAsState()
    var myUserProfile = firestoreViewModel?.myUserProfile?.collectAsState()

    var showLoading by remember{ mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.Black
//                    brush = Brush.verticalGradient(
//                        colors = listOf(
//
//                            Color(0xFF000000),
//                            Color(0xFFFFFFFF)
//                        )
//                    )
                )
        )
        {
            Icon(
                modifier= Modifier
                    .align(Alignment.TopStart)
                    .padding(18.dp)
                    .size(24.dp)
                    .clickable { navController?.popBackStack() },
                painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
                contentDescription ="Back button",
                tint = Color(0xFFFF5722)
            )

            Column(
                modifier = Modifier.padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Welcome message
                Text(
                    modifier = Modifier.padding(vertical = 128.dp),
                    text = "SignUp",
                    fontSize = 36.sp,
                    fontFamily = helveticaFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                // Phone number input field
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = {
                            Text(
                                text = "Name",
                                fontSize = 18.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Color.White,
                            textColor = Color.White,
                            backgroundColor = Color.DarkGray,
                            focusedBorderColor = Color(0xFF0890CD),
                            unfocusedBorderColor = Color.White,
                            disabledTextColor = Color.White,
                            focusedLabelColor = Color(0xFF0890CD),
                            placeholderColor = Color.White

                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = userName,
                        onValueChange = { userName = it },
                        label = {
                            Text(
                                text = "Username",
                                fontSize = 18.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Color.White,
                            textColor = Color.White,
                            backgroundColor = Color.DarkGray,
                            focusedBorderColor = Color(0xFF0890CD),
                            unfocusedBorderColor = Color.White,
                            disabledTextColor = Color.White,
                            focusedLabelColor = Color(0xFF0890CD),
                            placeholderColor = Color.White

                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = {
                            Text(
                                text = "Phone number",
                                fontSize = 18.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Color.White,
                            textColor = Color.White,
                            backgroundColor = Color.DarkGray,
                            focusedBorderColor = Color(0xFF0890CD),
                            unfocusedBorderColor = Color.White,
                            disabledTextColor = Color.White,
                            focusedLabelColor = Color(0xFF0890CD),
                            placeholderColor = Color.White

                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = {
                            Text(
                                text = "Email",
                                fontSize = 18.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Color.White,
                            textColor = Color.White,
                            backgroundColor = Color.DarkGray,
                            focusedBorderColor = Color(0xFF0890CD),
                            unfocusedBorderColor = Color.White,
                            disabledTextColor = Color.White,
                            focusedLabelColor = Color(0xFF0890CD),
                            placeholderColor = Color.White

                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(
                                text = "Password",
                                fontSize = 18.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                Icon(
                                    painter = painterResource(id = if (passwordVisibility) R.drawable.round_visibility_24 else R.drawable.round_visibility_off_24),
                                    contentDescription = if (passwordVisibility) "Hide Password" else "Show Password"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Color.White,
                            textColor = Color.White,
                            backgroundColor = Color.DarkGray,
                            focusedBorderColor = Color(0xFF0890CD),
                            unfocusedBorderColor = Color.White,
                            disabledTextColor = Color.White,
                            focusedLabelColor = Color(0xFF0890CD),
                            placeholderColor = Color.White

                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = {
                            Text(
                                text = "Confirm Password",
                                fontSize = 18.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        },
                        visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisibility = !confirmPasswordVisibility }) {
                                Icon(
                                    painter = painterResource(id = if (confirmPasswordVisibility) R.drawable.round_visibility_24 else R.drawable.round_visibility_off_24),
                                    contentDescription = if (confirmPasswordVisibility) "Hide Password" else "Show Password"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = Color.White,
                            textColor = Color.White,
                            backgroundColor = Color.DarkGray,
                            focusedBorderColor = if(password== confirmPassword){Color(0xFF0890CD)} else Color.Red,
                            unfocusedBorderColor = Color.White,
                            disabledTextColor = Color.White,
                            focusedLabelColor = Color(0xFF0890CD),
                            placeholderColor = Color.White

                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val mapData = mapOf(
                            "email" to email,
                            "phone" to phone,
                            "userName" to userName,
                            "name" to name,
                            "profilePhotoUrl" to "",
                            "favoriteArtists" to listOf(""),
                            "featuredAudio" to "",
                            "myLanguages" to listOf("")
                        )
                        // SignUp
                        viewModel?.signup(name, email, password)
                        scope.launch{
                            firestoreViewModel?.createUserProfile(userName = userName, mapData = mapData)
                           firestoreViewModel?.getMyUserProfile(email = email)
                        }





                    },
                    enabled= password == confirmPassword,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Text(
                        text = "Signup",
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
                Text(
                    text = "I already have an account, Login",
                    fontSize = 18.sp,
                    fontFamily = helveticaFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF0890CD),
                    modifier = Modifier
                        .clickable
                        {
                            navController?.navigate("LoginAuthScreen")
                        }

                )
                myUserProfile?.value?.let {
                    when (it) {
                        is Resource.Failure -> {
                            val context = LocalContext.current
                            Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                        }

                        Resource.Loading -> {
                            CircularDotsAnimation()
                        }

                        is Resource.Success -> {
                            LaunchedEffect(Unit){
                                navController?.navigate("ProfileScreenLanguage")
                            }


                        }
                    }
                }

                signupFlow?.value?.let {
                    when (it) {
                        is Resource.Failure -> {
                            val context = LocalContext.current
                            Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                        }

                        Resource.Loading -> {
                           CircularDotsAnimation()
                        }

                        is Resource.Success -> {
//                            LaunchedEffect(Unit) {
//                                navController?.navigate("LoginAuthScreen") {
//                                    popUpTo("SignUpScreen") { inclusive = true }
//                                }
//                            }
                        }
                    }
                }
            }

        }
    }

}

@Preview
@Composable
fun SignUpPreview() {
    SignUpScreen(null, firestoreViewModel = null,rememberNavController())

}