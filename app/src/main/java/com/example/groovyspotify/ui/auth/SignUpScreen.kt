package com.example.groovyspotify.ui.auth

import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.groovyspotify.R
import com.example.groovyspotify.common.composable.BasicFieldComposable
import com.example.groovyspotify.common.composable.ColoredText
import com.example.groovyspotify.common.composable.CommonButton
import com.example.groovyspotify.common.composable.CommonProgressSpinner
import com.example.groovyspotify.common.composable.CommonText
import com.example.groovyspotify.common.composable.EmailFieldComposable
import com.example.groovyspotify.common.composable.ExtraBoldText
import com.example.groovyspotify.common.composable.PasswordFieldComposable
import com.example.groovyspotify.common.composable.RepeatPasswordFieldComposable
import com.example.groovyspotify.common.composable.displayToast
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.services.common.Event
import com.example.groovyspotify.ui.fcm.FCMViewModel
import com.example.groovyspotify.ui.home.CircularDotsAnimation
import com.example.groovyspotify.ui.profilescreens.FirestoreViewModel
import com.example.groovyspotify.ui.realtimedatabase.RealtimeDatabaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import font.helveticaFamily
import kotlinx.coroutines.launch
import com.example.groovyspotify.R.string as AppText


@Composable
fun SignUpScreen(
    signupViewModel: SignUpViewModel ,
    openAndPopUp: (String, String) -> Unit

    ) {

    val uiState by signupViewModel.uiState
    val loading by signupViewModel.signUpInProgress
    val toastMsgEvent by signupViewModel.poppy
    // Background gradient
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
            ExtraBoldText(text = stringResource(id = AppText.sign_up),modifier = Modifier.padding(top =128.dp))

            // Phone number input field


            Spacer(modifier = Modifier.height(8.dp))

            BasicFieldComposable(
                value = uiState.userName,
                onNewValue =
                signupViewModel::onUserNameChange,
                modifier = Modifier, text = AppText.username,

            )

            Spacer(modifier = Modifier.height(8.dp))

            EmailFieldComposable(
                value = uiState.email,
                onNewValue =
                signupViewModel::onEmailChange,
                modifier = Modifier
            )


            Spacer(modifier = Modifier.height(8.dp))

            PasswordFieldComposable(
                value = uiState.password,
                onNewValue =
                signupViewModel::onPasswordChange,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(8.dp))
            RepeatPasswordFieldComposable(
                value = uiState.repeatPassword,
                onNewValue =
                signupViewModel::onRepeatPasswordChange,
                modifier = Modifier
            )


            Spacer(modifier = Modifier.height(16.dp))

            CommonButton(
                text = stringResource(id = AppText.create_account),
                modifier = Modifier,
                color = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                enabled = signupViewModel.ColorOfSignupButton()
            ) {
                signupViewModel.onSignUpClick(openAndPopUp)
            }

            ColoredText(
                text = "I already have an account, Login",
                color = Color(0xFF0890CD),
                modifier = Modifier
            ) {
                    signupViewModel.onLoginScreenButtonClick(openAndPopUp)
            }

            toastMsgEvent?.let { displayToast(toastMsgEvent = it) }
        }
        if(loading)
            CommonProgressSpinner()
    }
}

//@Preview
//@Composable
//fun SignUpPreview() {
//    SignUpScreen()
//
//}