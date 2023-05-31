package com.example.groovyspotify.ui.profilescreens

import androidx.compose.runtime.Composable

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.groovyspotify.model.firestore.Contact
import font.helveticaFamily
import kotlinx.coroutines.launch

@Composable
fun ContactSyncScreen() {
    val context = LocalContext.current
    var contacts by remember { mutableStateOf(emptyList<Contact>()) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Permission granted, perform contact synchronization
                loadContacts(context = context)
            }
        }
    )
    val syncContactsEnabled =
        remember(hasReadContactsPermission(context)) { hasReadContactsPermission(context) }
    if (syncContactsEnabled) {
        contacts = loadContacts(context)
    }


//    Button(
//        onClick = {
//            if (syncContactsEnabled) {
//                contacts = loadContacts(context)
//            } else {
//                requestReadContactsPermission(this)
//            }
//        },
//        enabled = syncContactsEnabled
//    ) {
//        if (syncContactsEnabled) {
//            Text(text = "Sync Contacts")
//        } else {
//            Text(text = "Grant Permission")
//        }
//    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5722)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    if (syncContactsEnabled) {
                        // Permission already granted, perform contact synchronization
                        loadContacts(context)
                    } else {
                        // Permission not granted, request permission
                        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                    }
                },
//                enabled = syncContactsEnabled
            ) {
                if (syncContactsEnabled) {

                    Text(
                        text = "Sync Contacts",
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium
                    )
                } else {

                    Text(
                        text = "Grant Permission",
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(contacts) { contact ->
                    Text(
                        text = contact.name,
                        fontSize = 18.sp,
                        fontFamily = helveticaFamily,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

private fun hasReadContactsPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_CONTACTS
    ) == PackageManager.PERMISSION_GRANTED
}

private fun loadContacts(context: Context): List<Contact> {
    val contacts = mutableStateListOf<Contact>()

    val cursor: Cursor? = context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        null
    )

    cursor?.use {
        val nameIndex: Int = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val numberIndex: Int = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

        while (it.moveToNext()) {
            val name: String = it.getString(nameIndex)
            val number: String = it.getString(numberIndex)

            val contact = Contact(name, number)
            contacts.add(contact)
        }
    }

    return contacts
}

@Preview
@Composable
fun ContactsSyncScreenPreview() {
    ContactSyncScreen()
}