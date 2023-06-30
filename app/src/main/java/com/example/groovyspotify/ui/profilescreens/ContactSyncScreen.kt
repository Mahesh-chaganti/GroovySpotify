package com.example.groovyspotify.ui.profilescreens

import androidx.compose.runtime.Composable

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
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
import com.example.groovyspotify.model.firestore.UserContact
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.ui.auth.AuthViewModel
import com.example.groovyspotify.ui.fcm.FCMViewModel
import com.example.groovyspotify.ui.home.CircularDotsAnimation
import com.example.groovyspotify.ui.realtimedatabase.RealtimeDatabaseViewModel
import font.helveticaFamily
import kotlinx.coroutines.launch
import java.sql.Time
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContactSyncScreen(
    viewModel: AuthViewModel?,
    firestoreViewModel: FirestoreViewModel?,
    fcmViewModel: FCMViewModel,
    realtimeDatabaseViewModel: RealtimeDatabaseViewModel
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val contacts = loadContacts(context = context)
    var myUserProfile = firestoreViewModel?.myUserProfile?.collectAsState()
    var matchedContacts by remember { mutableStateOf(ArrayList<UserContact>()) }
    var matchedContactsAll = contacts.toSet().toList()
    Log.d(
        "Least no of contacts",
        "ContactSyncScreen: ${matchedContactsAll.size} and $matchedContactsAll "
    )
    val listOfUsers = firestoreViewModel?.listOfRecommendedProfiles?.collectAsState()
    Log.d("Users", "ContactSyncScreen: $listOfUsers")

    listOfUsers?.value.let {
        when (it) {
            is Resource.Failure -> {

            }

            is Resource.Loading -> {
                CircularDotsAnimation()
            }

            is Resource.Success -> {
                try {
                    it.data.forEachIndexed { index, item ->


                        matchedContactsAll.forEach { contact ->
                            if (contact.number?.contains(item.phone) == true) {
                                if (matchedContacts.isEmpty()) {
                                    matchedContacts.add(
                                        UserContact(
                                            item.userName,
                                            item.name,
                                            contact.number
                                        )
                                    )
                                    Log.d(
                                        "Each Contact",
                                        "ContactSyncScreen: ${item.name} and $contact"
                                    )

                                } else {
                                    matchedContacts.forEach {
                                        if (it.phone?.contains(item.phone) == true) {

                                        } else {
                                            matchedContacts.add(
                                                UserContact(
                                                    item.userName,
                                                    item.name,
                                                    contact.number
                                                )
                                            )
                                            Log.d(
                                                "Each Contact",
                                                "ContactSyncScreen: ${item.name} and $contact"
                                            )
                                        }
                                    }
                                }


                            }
                        }


                    }
                    Log.d("matching", "ContactSyncScreen: ${matchedContacts}")


                } catch (e: Exception) {
                    Log.d("Contact error", "ContactSyncScreen: $e")


                }
            }

            else -> {}
        }

    }
    if (matchedContacts.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Log.d("matching", "ContactSyncScreen: ${matchedContacts.size}")

            val matchingContacts = matchedContacts.toSet().toList()
            myUserProfile?.value.let {
                when (it) {
                    is Resource.Failure -> {
                        val context = LocalContext.current
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                    }

                    Resource.Loading -> {
                        CircularDotsAnimation()
                    }

                    is Resource.Success -> {
                        FilteredContactList(
                            filteredContacts = matchingContacts,
                            fcmViewModel = fcmViewModel,
                            realtimeDatabaseViewModel = realtimeDatabaseViewModel,
                            myUserProfile = it.data
                        )
                        Log.d("matching", "ContactSyncScreen: ${matchingContacts.size}")
                    }

                    else -> {}
                }
            }


        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FilteredContactList(
    filteredContacts: List<UserContact>,
    fcmViewModel: FCMViewModel?,
    realtimeDatabaseViewModel: RealtimeDatabaseViewModel?,
    myUserProfile: UserProfile
) {

    if (filteredContacts.isNotEmpty()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Text(
                    modifier = Modifier.padding(top = 36.dp),
                    text = "Friends",
                    fontSize = 36.sp,
                    fontFamily = helveticaFamily,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
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
                            .height(60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    )
                    {


                        contact.name?.let {
                            Text(
                                text = it,
                                fontSize = 18.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }

                        contact.phone?.let {
                            Text(
                                text = it,
                                fontSize = 18.sp,
                                fontFamily = helveticaFamily,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }


                    Button(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(40.dp),
                        shape = RoundedCornerShape(24.dp),
                        onClick = {
                            contact.userName?.let { fcmViewModel?.sendNotificationToUser(userName = it) }
                            realtimeDatabaseViewModel?.sendFriendRequest(
                                receiverUsername = contact.userName,
                                senderUsername = myUserProfile.userName,
                                status = "pending",
                                time = LocalDateTime.now().toString()
                            )

                        },
                        colors = ButtonDefaults
                            .buttonColors(

                                backgroundColor = Color(0xFFFF3A20),
                                contentColor = Color.White


                            )
                    ) {
                        Text(
                            text = "Add",
                            fontSize = 16.sp,
                            fontFamily = helveticaFamily,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium
                        )

                    }

                }
                Divider(modifier = Modifier.height(2.dp))
            }
        }

    }
}


fun loadContacts(context: Context): List<Contact> {
    val contacts = mutableListOf<Contact>()

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
    Log.d("All Contacts", "loadContacts: ${contacts.size}")

    return contacts
}

private fun hasReadContactsPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_CONTACTS
    ) == PackageManager.PERMISSION_GRANTED
}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun ContactsSyncScreenPreview() {
//    val dummyContacts = listOf(
//        UserContact("Kohli", "name","12345"),
//        UserContact("Kobe", "56789",""),
//        UserContact("Kyrie", "2468","")
//    )
//
//    FilteredContactList(filteredContacts = dummyContacts, fcmViewModel = null,realtimeDatabaseViewModel = null, myUserProfile = )
//}