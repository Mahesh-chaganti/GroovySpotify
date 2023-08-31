package com.example.groovyspotify.network

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class FCMApiRequest {
    val serverKey = "YOUR_FCM_SERVER_KEY"
    val targetUserFCMToken = "TARGET_USER_FCM_TOKEN"
    val notificationTitle = "Friend Request"
    val notificationBody = "You have received a friend request!"



    private val json = """
    {
        "to": "$targetUserFCMToken",
        "notification": {
            "title": "$notificationTitle",
            "body": "$notificationBody"
        }
    }
""".trimIndent()

    private val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())

    val request = Request.Builder()
        .url("https://fcm.googleapis.com/fcm/send")
        .addHeader("Authorization", "key=$serverKey")
        .post(requestBody)
        .build()

    val client: OkHttpClient =  OkHttpClient()
    fun requestCall(){
        try {
            val response = client.newCall(request).execute()
            // Handle the response if needed
        } catch (e: IOException) {
            // Handle the failure if needed
        }
    }


}