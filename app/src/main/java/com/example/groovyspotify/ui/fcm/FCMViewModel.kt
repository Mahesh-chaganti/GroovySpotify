package com.example.groovyspotify.ui.fcm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class FCMViewModel @Inject constructor(): ViewModel(){



    private val _fcmToken = MutableStateFlow<String>("")
    val fcmToken : StateFlow<String> = _fcmToken

    fun fcmMessagingToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
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
    fun sendNotificationToUser(phone: String){


        val user2FcmToken = "USER2_FCM_TOKEN" // Replace with the actual FCM token of User 2


        val data = mutableMapOf<String, String>()
        data["title"] = "New Message"
        data["body"] = "You have a new message from User 1"

        val message = RemoteMessage.Builder(user2FcmToken)
            .setData(data)
            .build()

        FirebaseMessaging.getInstance().send(message)
    }

}