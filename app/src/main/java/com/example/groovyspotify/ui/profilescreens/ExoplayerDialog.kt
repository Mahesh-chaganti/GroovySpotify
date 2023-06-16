package com.example.groovyspotify.ui.profilescreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.groovyspotify.ui.exoplayer.ExoplayerAnimation

@Composable
fun ExoplayerDialog() {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = {
                Text(
                    "This area typically contains the supportive text " +
                            "which presents the details regarding the Dialog's purpose."
                )

            },
            text = {

                    ExoplayerAnimation(isAnimating = true , modifier = Modifier.size(128.dp))

                

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Dismiss")
                }
            },
            backgroundColor = Color.Black
        )
    }
}

@Preview
@Composable
fun ExoplayerDialogPreview() {
    ExoplayerDialog()
}