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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.groovyspotify.data.utils.Resource

@Composable
fun SignUpScreen(viewModel: AuthViewModel?,navController: NavController?) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var signupFlow = viewModel?.signupFlow?.collectAsState()
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
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    navController?.navigate("HomeScreen")
                    // Send OTP
                    viewModel?.signup(name, email, password)

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Signup", fontSize = 18.sp)
            }
            Text(
                text = "I already have an account, Login",
                style = MaterialTheme.typography.body2,
                fontSize = 12.sp,
                modifier = Modifier.clickable { navController?.navigate("LoginAuthScreen") }

            )
            signupFlow?.value?.let {
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
                                popUpTo("SignUpScreen") { inclusive = true }
                            }
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
    SignUpScreen(null, rememberNavController())

}