package com.example.groovyspotify

import android.app.Activity
import android.content.ContentValues
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

@Composable
fun OTPScreen(
    viewModel: AuthViewModel?,
    navController: NavController?
) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var timeLeft by remember { mutableStateOf(59) }
    val context = LocalContext.current
    var storedVerificationId: String? = null
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    var verificationInProgress = false
    var verificationCode by remember { mutableStateOf("") }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("", "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Auto-retrieval or instant verification is successful
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(exception: FirebaseException) {
            // Verification failed
            Toast.makeText(context as Activity, "Verification failed: ${exception.message}", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            // Code sent successfully
            // Save the verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            resendToken = token
        }
    }
    fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?,
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(context as Activity) // (optional) Activity for callback binding
            // If no activity is passed, reCAPTCHA verification can not be used.
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }
    // Start the countdown timer when the screen loads
    LaunchedEffect(true) {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                // Handle timer finish event here
            }
        }.start()
    }
    fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // Create a PhoneAuthCredential with the verification code and verification ID
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)

        // Sign in with the credential
        signInWithPhoneAuthCredential(credential)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFA500),
                        Color(0xFF87CEEB)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(
                text = "OTP Verification",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = verificationCode,
                onValueChange = {verificationCode = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                label = {
                    Text(
                        text = "Enter OTP",
                        color = Color.White
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                textStyle = TextStyle(
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Resend OTP",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.White,
                    modifier = Modifier.clickable {
                        timeLeft = 59

                    }
                )
                Text(
                    text = "00:${timeLeft.toString().padStart(2, '0')}",
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { verifyPhoneNumberWithCode(storedVerificationId, verificationCode ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF87CEEB),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Verify",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}


@Preview
@Composable
fun OtpVerificationPreview() {
    OTPScreen(null, rememberNavController())
}