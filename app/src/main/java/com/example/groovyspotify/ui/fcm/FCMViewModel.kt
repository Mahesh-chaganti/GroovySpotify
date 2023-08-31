package com.example.groovyspotify.ui.fcm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.groovyspotify.model.realtimedatabase.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class FCMViewModel @Inject constructor(): ViewModel(){





    private val firebaseMessaging = FirebaseMessaging.getInstance()
    private val database: DatabaseReference = Firebase.database.reference

    private val _userFriend = MutableStateFlow(User())
    val userFriend : StateFlow<User> = _userFriend

    private val _fcmToken = MutableStateFlow<String>("")
    val fcmToken : StateFlow<String> = _fcmToken

    fun getFCMToken(){


        // Handle token refresh
        // You can send the new token to your server or perform other actions
        firebaseMessaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            _fcmToken.value = task.result

            // Log and toast
            val msg = "This is the ${_fcmToken.value}"
            Log.d("Token message", msg)

        })

    }
    fun sendNotificationToUser(userName: String){
        database.child("Users").child("User: $userName").get().addOnSuccessListener {
            _userFriend.value = it.value as User
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
//            val realtimeDbUsers: DatabaseReference = Firebase.database.reference.child("/Users/User: $userName")
//
//            val postListener = object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    // Get Post object and use the values to update the UI
//                    _userFriend.value = dataSnapshot.getValue<User>()!!
//                    // ...
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    // Getting Post failed, log a message
//                    Log.w("error", "loadUserDetails :onCancelled", databaseError.toException())
//                }
//            }
//            Log.d("user retrieval failed", "sendNotificationToUser: ${_userFriend.value}")
//            realtimeDbUsers.addValueEventListener(postListener)
//        }catch(e: Exception){
//            Log.d("user retrieval failed", "sendNotificationToUser: $e")
//        }



        val user2FcmToken = _userFriend.value.fcmToken// Replace with the actual FCM token of User 2
        Log.d("user2'sToken", "sendNotificationToUser: $user2FcmToken")
        val data = mutableMapOf<String, String>()
        data["title"] = "Vibe request"
        data["body"] = "Hello kobe"

        val message = RemoteMessage.Builder(user2FcmToken)
            .setData(data)
            .build()

        firebaseMessaging.send(message)

    }




}
