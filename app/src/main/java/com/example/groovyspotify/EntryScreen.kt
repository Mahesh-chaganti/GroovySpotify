package com.example.groovyspotify

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

@Composable
fun EntryScreen(navController: NavController?) {


    var phoneNumber by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var showTimer by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf(0) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black,
        contentColor = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Enter your phone number", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Email/Phone number") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (showTimer) {
                Text(
                    text = "Code will expire in $remainingTime seconds",
                    style = MaterialTheme.typography.body1
                )
            } else {
                Button(

                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        // Send verification code
                        // Set showTimer and remainingTime
                        showTimer = true
                        remainingTime = 60
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF308EE0))
                ) {
                    Text(text = "Send verification code")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Verification code") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF30E030)),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    // Verify code and sign in
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign in")
            }
        }


    }
}

@Preview
@Composable
fun EntryPreview() {
    EntryScreen(navController = null)
}