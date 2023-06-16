package com.example.groovyspotify.ui.profilescreens

import androidx.compose.runtime.Composable

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.groovyspotify.data.utils.Resource
import com.example.groovyspotify.model.firestore.Contact
import com.example.groovyspotify.model.firestore.MatchedContact
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.example.groovyspotify.ui.home.CircularDotsAnimation
import font.helveticaFamily
import kotlinx.coroutines.launch

@Composable
fun ContactSyncScreen(viewModel: AuthViewModel?, firestoreViewModel: FirestoreViewModel?) {

}



@Composable
fun FilteredContactList( filteredContacts: List<Contact>) {



    if (filteredContacts.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(filteredContacts) { contact ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    )
                    {


                        Text(
                            text = contact.name,
                            fontSize = 18.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )

                        Text(
                            text = contact.number,
                            fontSize = 18.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }


                    Button(onClick = {

                    }) {
                        Text(
                            text = "Add",
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
}


fun loadContacts(context: Context): ArrayList<Contact> {
    val contacts by mutableStateOf( ArrayList<Contact>())

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
private fun hasReadContactsPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_CONTACTS
    ) == PackageManager.PERMISSION_GRANTED
}

@Preview
@Composable
fun ContactsSyncScreenPreview() {
    ContactSyncScreen(null, null)
}