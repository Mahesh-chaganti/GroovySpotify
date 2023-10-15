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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.groovyspotify.R
import com.example.groovyspotify.data.googleAuth.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.groovyspotify.common.composable.EmailFieldComposable
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import font.helveticaFamily
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.groovyspotify.common.composable.CheckSignedIn
import com.example.groovyspotify.common.composable.ColoredText
import com.example.groovyspotify.common.composable.CommonButton
import com.example.groovyspotify.common.composable.CommonProgressSpinner
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.common.composable.ExtraBoldText
import com.example.groovyspotify.common.composable.PasswordFieldComposable
import com.example.groovyspotify.common.composable.displayToast
import com.example.groovyspotify.ui.home.CircularDotsAnimation
import javax.annotation.CheckForSigned
import com.example.groovyspotify.R.string as AppText


@Composable
fun LoginAuthScreen(
    loginViewModel: LoginViewModel,
    openAndPopUp: (String,String) -> Unit,
    openScreen:(String)->Unit

) {
    val uiState by loginViewModel.uiState
    val loading by loginViewModel.loginInProgress

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val toastMsgEvent by loginViewModel.poppy


    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(

            oneTapClient = Identity.getSignInClient(context)
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {

            scope.launch {
                try {
                    val googleCredential = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    if (googleCredential != null) {
                        loginViewModel.onGoogleSignInClick(credential = googleCredential)
                    }
                    Toast.makeText(context, "Sign In Successful", Toast.LENGTH_LONG).show()
//
                } catch (e: Exception) {
                    Log.d("launcherActivityResult", "LoginAuthScreen:$e ")
                }

            }
//        }


        }
    }

    CheckSignedIn(loginViewModel = loginViewModel, openScreen = openScreen)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black
            )
    )
    {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Welcome message
            ExtraBoldText(
                text = stringResource(id = AppText.log_in),
                modifier = Modifier.padding(top = 128.dp)
            )
            EmailFieldComposable(
                uiState.email,
                loginViewModel::onEmailChange,
                Modifier)
            Spacer(modifier = Modifier.height(8.dp))
            PasswordFieldComposable(uiState.password,
                    loginViewModel::onPasswordChange,
                Modifier)
            Spacer(modifier = Modifier.height(8.dp))
            ColoredText(
                text = stringResource(id = AppText.forgot_password),
                color = Color(0xFF0890CD),
                modifier = Modifier
            ) {
                //forgot password: recovery link
                loginViewModel.onForgotPasswordClick()
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Login button
            CommonButton(
                text = stringResource(id = AppText.log_in),
                modifier = Modifier,
                color =ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)) ,
                enabled = true
            ) {
                loginViewModel.onSignInClick(openAndPopUp)



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
                CommonText(text = "Or", modifier =Modifier )
                Divider(
                    color = Color.White,
                    modifier = Modifier
                        .size(width = 150.dp, height = 1.dp)

                        .padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            CommonButton(
                text = "Continue with google",
                modifier = Modifier,
                color = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                enabled = true
            ) {
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
            }
            //continue with google
//                    val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestEmail()
//                        .requestIdToken(Constant.clientId)
//                        .build()
//
//                    val googleSingInClient = GoogleSignIn.getClient(context, gso)
//
//                    launcher.launch(googleSingInClient.signInIntent)

            Spacer(modifier = Modifier.height(8.dp))

            ColoredText(text = "Don't have an account, signup", color = Color(0xFF0890CD), modifier = Modifier) {
                loginViewModel.onSignUpScreenButtonClick(openAndPopUp)
            }
         toastMsgEvent?.let { displayToast(toastMsgEvent = it) }

        }
        if (loading)
           CommonProgressSpinner()
    }
}



//@Preview
//@Composable
//fun PhoneAuthPreview() {
//    LoginAuthScreen( )
//}
