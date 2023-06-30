package com.example.groovyspotify.ui.realtimedatabase

import androidx.lifecycle.ViewModel
import com.example.groovyspotify.model.realtimedatabase.FriendRequest
import com.example.groovyspotify.model.realtimedatabase.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RealtimeDatabaseViewModel @Inject constructor(): ViewModel(){
        val realtimeDb: DatabaseReference = Firebase.database.reference

        fun sendFriendRequest(senderUsername: String, receiverUsername: String,status: String, time: String){
                realtimeDb.child("FriendRequests")
                        .child("FriendRequest by: $senderUsername")
                        .setValue(FriendRequest(senderUsername,receiverUsername,status,time))
        }
        fun sendUserData(userName: String,phone: String, fcmToken: String, friends: List<User>){
                realtimeDb.child("Users")
                        .child("User: $userName")
                        .setValue(User(userName,phone,fcmToken,friends))
        }

}