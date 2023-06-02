package com.example.groovyspotify.ui.auth

import android.app.Activity.RESULT_OK
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.groovyspotify.R
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.data.googleAuth.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.google.firebase.firestore.FirebaseFirestore
import font.helveticaFamily


@Composable
fun LoginAuthScreen(viewModel: AuthViewModel?, firestoreViewModel: FirestoreViewModel?,navController: NavController?) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var loginFlow = viewModel?.loginFlow?.collectAsState()
    val context = LocalContext.current
    var passwordVisibility by remember { mutableStateOf(false) }

    var googleSignInFlow = viewModel?.googleState?.collectAsState()
    val scope = rememberCoroutineScope()

//    LaunchedEffect(key1 = Unit) {
//        if(viewModel?.currentUser!= null) {
//            navController?.navigate("HomeScreen")
//        }
//    }

    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(

            oneTapClient = Identity.getSignInClient(context)
        )
    }
//    val launcher =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
//            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
//            try {
//                val result = account.getResult(ApiException::class.java)
//                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
//                viewModel?.googleSignIn(credentials)
//                Toast.makeText(context, "Edo happened", Toast.LENGTH_LONG).show()
//            } catch (it: ApiException) {
//                print(it)
//                Log.d("launcherActivityResult", "LoginAuthScreen:$it ")
//            }
//        }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {

            scope.launch {
                try {
                    val googleCredential = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel?.googleSignIn(googleCredential!!)
                    Toast.makeText(context, "Sign In Successful", Toast.LENGTH_LONG).show()

                }catch (e: Exception){
                    Log.d("launcherActivityResult", "LoginAuthScreen:$e ")
                }

            }
        }
//
    }

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
//                            Color(0xFF000000),
//                            Color(0xFFFFFFFF)
//
//                        )
//                    )
                )
        )
        {

            Column(
                modifier = Modifier.padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Welcome message
                Text(
                    modifier = Modifier.padding(vertical = 128.dp),
                    text = "Welcome",
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
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(
                                text = "Email",
                            fontSize = 18.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        ) },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        modifier = Modifier.fillMaxWidth(),
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

                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                Icon(
                                    painter = painterResource(id = if (passwordVisibility) R.drawable.round_visibility_24 else R.drawable.round_visibility_off_24),
                                    contentDescription = if (passwordVisibility) "Hide Password" else "Show Password"
                                )
                            }
                        },
                        label = { Text(
                            text = "Password",
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
                        modifier = Modifier.fillMaxWidth(),
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

                        )
                    )
                }
//                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Checkbox(
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFFF5722),
                                uncheckedColor = Color.White,
                                checkmarkColor = Color(0xFFFF5722),
                                disabledColor = Color.DarkGray
                            ),
                            checked = rememberMe,
                            onCheckedChange = { isChecked -> rememberMe = isChecked },

                            )
                        Text(
                            text = "Remember me",
                            fontSize = 18.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }


                        Text(
                            modifier= Modifier.clickable {  },
                            text = "Forgot Password",
                            fontSize = 18.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF0091D4)
                        )

                }

                Spacer(modifier = Modifier.height(32.dp))

                // Login button
                Button(
                    onClick = {
//                        val firestore = FirebaseFirestore.getInstance()
//                        viewModel?.login(email = email, password = password)
//                        val userProfileTest = UserProfile(
//                            userName = "test1",
//                            name = "Test1",
//                            email = "test1@gmail.com",
//                            phone = "09182436",
//                            profilePhotoUri = Uri.EMPTY,
//                            favoriteArtists = listOf("Justin Bieber","DSP","Selena"),
//                            featuredAudio = "Paata",
//                            myLanguages = listOf("Telugu","Hindi","English")
//
//                        )
//                        firestore.collection("UserProfiles")
//                            .document("test1")
//                            .set(userProfileTest)
                        navController?.navigate("ProfileScreenLanguage")

                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Login",
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Divider(
                        color = Color.White,
                        modifier = Modifier
                            .size(width = 150.dp, height = 1.dp)

                            .padding(horizontal = 16.dp)
                    )
                    Text(

                        text = "Or",
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Divider(
                        color = Color.White,
                        modifier = Modifier
                            .size(width = 150.dp, height = 1.dp)

                            .padding(horizontal = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

//                    //continue with google
//                    val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestEmail()
//                        .requestIdToken(Constant.clientId)
//                        .build()
//
//                    val googleSingInClient = GoogleSignIn.getClient(context, gso)
//
//                    launcher.launch(googleSingInClient.signInIntent)

                        scope.launch {

                            try {
                                val signInIntentSender = googleAuthUiClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                    ).build()
                                )

                            } catch (e: Exception) {
                                Log.d("buttonClick", "LoginAuthScreen:$e ")
                            }
                        }


                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                    ,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .size(40.dp),
                        painter = painterResource(id = R.drawable.img),
                        contentDescription = null)
                    Text(
                        text = "Continue with Google",
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
//                Button(
//                    onClick = {
//
//
//                    },
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(64.dp),
//                    shape = RoundedCornerShape(16.dp)
//                ) {
//                    Icon(
//                        modifier = Modifier.padding(horizontal = 8.dp),
//                        painter = painterResource(id = R.drawable.img_1),
//                        contentDescription = null
//                    )
//                    Text(text = "Continue with Phone", fontSize = 18.sp)
//                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "Don't have an account?",
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                    )
                    Text(
                        text = "SignUp",
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium,
                        color =Color(0xFF0091D4),
                        modifier = Modifier.clickable { navController?.navigate("SignUpScreen") }

                    )
                }
            }
            loginFlow?.value.let {
                when (it) {
                    is Resource.Failure -> {
                        val context = LocalContext.current
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    }

                    Resource.Loading -> {
                        CircularProgressIndicator()
                    }

                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navController?.navigate("ProfileScreenLanguage") {
                                popUpTo("LoginAuthScreen") {
                                    inclusive = true
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }

            googleSignInFlow?.value.let {
                when (it) {
                    is Resource.Failure -> {
                        val context = LocalContext.current
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    }

                    Resource.Loading -> {
                        CircularProgressIndicator()
                    }

                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navController?.navigate("ProfileScreenLanguage") {
                                popUpTo("LoginAuthScreen") {
                                    inclusive = true
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }

        }
    }
}

@Preview
@Composable
fun PhoneAuthPreview() {
    LoginAuthScreen(null, firestoreViewModel = null,rememberNavController())
}
