package com.example.groovyspotify.ui.auth

import android.app.Activity.RESULT_OK
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


@Composable
fun LoginAuthScreen(viewModel: AuthViewModel?, navController: NavController?) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var loginFlow = viewModel?.loginFlow?.collectAsState()
    val context = LocalContext.current

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
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF000000),
                            Color(0xFFFFFFFF)

                        )
                    )
                )
        )

        Column(
            modifier = Modifier.padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Welcome message
            Text(
                text = "Welcome",
                style = MaterialTheme.typography.h4,
                color = Color.White,
                modifier = Modifier.padding(vertical = 64.dp)
            )

            // Phone number input field
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { isChecked -> rememberMe = isChecked },

                        )
                    Text(
                        text = "Remember Me",
                        style = MaterialTheme.typography.body1,
                        fontSize = 12.sp
                    )
                }

                Text(
                    text = "Forgot Password",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { /* Handle forgot password click */ },
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Get OTP button
            Button(
                onClick = {

                    viewModel?.login(email = email, password = password)

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Login", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .size(width = 150.dp, height = 1.dp)

                        .padding(horizontal = 16.dp)
                )
                Text(
                    text = "or",

                    )
                Divider(
                    color = Color.Black,
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

                           }catch (e: Exception){
                               Log.d("buttonClick", "LoginAuthScreen:$e ")
                           }
                        }


                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03A9F4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.img), contentDescription = null)
                Text(text = "Continue with Google", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {


                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03A9F4)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.img_1), contentDescription = null)
                Text(text = "Continue with Phone", fontSize = 18.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Don't have an account?",
                    style = MaterialTheme.typography.body2,
                    fontSize = 12.sp
                )
                Text(
                    text = "Signup",
                    style = MaterialTheme.typography.body2,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable { navController?.navigate("SignUpScreen") }

                )
            }
        }
        loginFlow?.value.let {
            when (it) {
                is Resource.Failure -> {
                    val context = LocalContext.current
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                }

                Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navController?.navigate("HomeScreen") {
                            popUpTo("HomeScreen") {
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
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                }

                Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navController?.navigate("HomeScreen") {
                            popUpTo("HomeScreen") {
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

@Preview
@Composable
fun PhoneAuthPreview() {
    LoginAuthScreen(null, rememberNavController())
}
