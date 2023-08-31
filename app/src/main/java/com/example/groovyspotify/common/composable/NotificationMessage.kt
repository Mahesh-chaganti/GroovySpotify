package com.example.groovyspotify.common.composable

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.groovyspotify.model.services.common.Event
import com.example.groovyspotify.model.services.common.HandleException
import com.example.groovyspotify.ui.ParentViewModel

@Composable
fun NotificationMessage(vm : ParentViewModel) {
    val notifState by vm.popupNotification
    val notifMessage = notifState?.getContentOrNull().toString()
    if (!notifMessage.isNullOrEmpty())
        Toast.makeText(LocalContext.current, notifMessage, Toast.LENGTH_SHORT).show()
    else
        Toast.makeText(LocalContext.current, "Sankanaku", Toast.LENGTH_SHORT).show()
}

@Composable
fun displayToast(toastMsgEvent: Event<String>){
    val toastMsg = toastMsgEvent?.getContentOrNull()


    if(!toastMsg.isNullOrEmpty()){

        Toast.makeText(LocalContext.current, toastMsg,Toast.LENGTH_SHORT).show()

    }

}