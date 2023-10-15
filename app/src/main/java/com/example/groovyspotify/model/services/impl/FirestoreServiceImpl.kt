package com.example.groovyspotify.model.services.impl

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.groovyspotify.model.firestore.COLLECTION_NAMES.COLLECTION_CHAT
import com.example.groovyspotify.model.firestore.COLLECTION_NAMES.COLLECTION_MESSAGES
import com.example.groovyspotify.model.firestore.COLLECTION_NAMES.COLLECTION_PROFILES
import com.example.groovyspotify.model.firestore.ChatData
import com.example.groovyspotify.model.firestore.ChatUser
import com.example.groovyspotify.model.firestore.Message
import com.example.groovyspotify.model.firestore.TrackData
import com.example.groovyspotify.model.firestore.UserProfile
import com.example.groovyspotify.model.profile.ProfileArtist
import com.example.groovyspotify.model.services.AccountService
import com.example.groovyspotify.model.services.FirestoreService
import com.example.groovyspotify.model.services.common.Event
import com.example.groovyspotify.model.spotifyapidata.track.Artist
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

class FirestoreServiceImpl @Inject constructor(
    private val authService: AccountService,
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore
) : FirestoreService {
    override var userData: UserProfile = UserProfile()
    var currentChatMessagesListener: ListenerRegistration? = null

    var chats :  List<ChatData> = listOf()

    var chatMessages :  List<Message> = listOf()



    override suspend fun getMyUserProfile(
        uid: String,
        handleException: (Exception?, String) -> Unit,
        handleSuccess: (UserProfile,String) -> Unit
    ): UserProfile {

        firestore.collection(COLLECTION_PROFILES).document(uid)
            .addSnapshotListener { value, error ->
                if (error != null)
                    handleException(error, "Cannot retrieve user data")

                if (value != null) {
                    userData = value.toObject<UserProfile>()?: UserProfile()
                    Log.d("got the user", "getMyUserProfile: $userData")

                    handleSuccess(userData,"Got the user")


                }

            }
        return userData
    }

    override suspend fun createOrUpdateMyUserProfile(
        mapData: Map<String, Any>,
        handleException: (Exception?, String) -> Unit,
        handleSuccess: (Exception?, String) -> Unit
    ) {
//        inProgress.value = true
//        val mapData1 = mapOf(
//            "userName" to mapData["userName"],
//            "email" to mapData["email"],
//            "uid" to authService.currentUserId,
//            "name" to  mapData["userName"],
//            "profilePhotoUrl" to mapData["profilePhotoUrl"],
//            "email" to mapData["email"],
//            "gender" to mapData["gender"],
//            "age" to mapData["age"],
//            "favLanguages" to if( mapData["favLanguages"] != null) mapData["favLanguages"] else listOf<String>(),
//            "favArtists" to if(mapData["favArtists"] != null) mapData["favArtists"] else listOf<ProfileArtist>() ,
//            "track" to if(mapData["track"] != null) mapData["track"] else TrackData(),
//
//        )
//        val userData1 = authService.currentUserId?.let {
//
//            UserProfile(
//                uid = it,
//                phone = mapData1["phone"].toString()?:userData.phone?:"",
//                name = mapData1["userName"].toString()?:userData.userName?:"",
//                userName = mapData1["userName"].toString() ?: userData.userName?:"",
//                profilePhotoUrl = mapData1["profilePhotoUrl"].toString()?: userData.profilePhotoUrl?:"",
//                email = mapData1["email"].toString()?: userData.email?:"",
//                gender = mapData1["gender"].toString()?:userData.gender?:"",
//                age = mapData1["age"].toString()?:userData.age?:"",
//                favLanguages = mapData1["favLanguages"] as List<String>?:userData.favLanguages?: listOf(""),
//                favArtists = mapData1["favArtists"] as List<ProfileArtist>?:userData.favArtists?: listOf(ProfileArtist()),
//                track = mapData1["track"] as TrackData?:userData.track?:TrackData(),
//
//            )
//        }
        authService.currentUserId?.let { uid ->
            firestore.collection(COLLECTION_PROFILES).document(uid)
                .get()
                .addOnSuccessListener {
                    if (it.exists())

                            it.reference.update(mapData)
                                .addOnSuccessListener {
                                    handleSuccess(null, "Updated user")
                    //                                   inProgress.value = false
                    //                                   populateCards()
                                }
                                .addOnFailureListener {
                                    handleException(it, "Cannot update user")
                                }

                    else {
                        val mapData1 = mapOf(
                            "userName" to mapData["userName"],
                            "email" to mapData["email"],
                            "name" to mapData["userName"],
                            "uid" to authService.currentUserId
                            )
                        firestore.collection(COLLECTION_PROFILES).document(uid).set(mapData1)
                        handleSuccess(null, "created user")
//                           inProgress.value = false
//                           getMyUserProfile()
                    }
                }
                .addOnFailureListener {
                    handleException(it, "Cannot create user")
                }
        }

    }

    override suspend fun uploadImageToStorage(imageUri: Uri, handleException: (Exception?, String) -> Unit, onSuccess:(Uri)->Unit) {


            val storageRef = storage.reference
            val uuid = UUID.randomUUID()
            val imageRef = storageRef.child("images/$uuid")
            val uploadTask = imageRef.putFile(imageUri)

            uploadTask
                .addOnSuccessListener {
                    val result = it.metadata?.reference?.downloadUrl
                    result?.addOnSuccessListener(onSuccess)
                }
                .addOnFailureListener {
                    handleException(it,"Uploading failed")
//                    inProgress.value = false
                }

    }


    override suspend fun onLike(
        selectedUser: UserProfile,
        handleException: (String) -> Unit,
        handleSuccess: (UserProfile) -> Unit
    ) {
        val reciprocalMatch = selectedUser.swipesRight.contains(authService.currentUserId)
        if (!reciprocalMatch) {
            firestore.collection(COLLECTION_PROFILES).document(authService.currentUserId ?: "")
                .update("swipesRight", FieldValue.arrayUnion(selectedUser.uid))
        } else {
            handleSuccess(selectedUser)

            firestore.collection(COLLECTION_PROFILES).document(selectedUser.uid ?: "")
                .update("swipesRight", FieldValue.arrayRemove(authService.currentUserId))
            firestore.collection(COLLECTION_PROFILES).document(selectedUser.uid ?: "")
                .update("matches", FieldValue.arrayUnion(authService.currentUserId))
            firestore.collection(COLLECTION_PROFILES).document(authService.currentUserId ?: "")
                .update("matches", FieldValue.arrayUnion(selectedUser.uid))


        }
    }
    override suspend fun addToChats(user1: UserProfile,user2:UserProfile){
        val chatKey = firestore.collection(COLLECTION_CHAT).document().id
        val chatData = ChatData(
            chatKey,
            ChatUser(
                user1.uid,
                if (user1.name.isNullOrEmpty()) user1.userName
                else user1.name,
                user1.profilePhotoUrl
            ),
            ChatUser(
                user2.uid,
                if (user2.name.isNullOrEmpty()) user2.userName
                else user2.name,
                user2.profilePhotoUrl
            )
        )
        firestore.collection(COLLECTION_CHAT).document(chatKey).set(chatData)
    }

    override suspend fun onDislike(selectedUser: UserProfile) {
        firestore.collection(COLLECTION_PROFILES).document(authService.currentUserId ?: "")
            .update("swipesLeft", FieldValue.arrayUnion(selectedUser.uid))
    }

    override suspend fun populateChats(  handleException: (Exception?, String) -> Unit,
                                         handleSuccess: (List<ChatData>) -> Unit) {
        firestore.collection(COLLECTION_CHAT).where(
            Filter.or(
                Filter.equalTo("user1.userId", userData.uid),
                Filter.equalTo("user2.userId", userData.uid)
            )
        )
            .addSnapshotListener { value, error ->
                if (error != null)
                    handleException(error,"Failed to retrieve")
                if (value != null)

                    chats = value.documents.mapNotNull { it.toObject<ChatData>() }
                handleSuccess(chats)

            }
    }

    override suspend fun populateChat(chatId: String, handleException: (Exception?, String) -> Unit,
                                      handleSuccess: (Exception?, String) -> Unit) {
        currentChatMessagesListener = firestore.collection(COLLECTION_CHAT)
            .document(chatId)
            .collection(COLLECTION_MESSAGES)
            .addSnapshotListener { value, error ->
                if (error != null)
                    handleException(error,"Failed to retreive")
                if (value != null)
                    chatMessages = value.documents
                        .mapNotNull { it.toObject<Message>() }
                        .sortedBy { it.timestamp }

            }
    }

    override suspend fun depopulateChat() {
        currentChatMessagesListener = null
        chatMessages = listOf()
    }

    override suspend fun onSendReply(chatId: String, message: String) {
        val time = Calendar.getInstance().time.toString()
        val message = Message(userData.uid, message, time)
        firestore.collection(COLLECTION_CHAT).document(chatId)
            .collection(COLLECTION_MESSAGES).document().set(message)
    }

    override suspend fun populateCards(
        handleException: (Exception?, String) -> Unit,

        handleSuccess: (List<UserProfile>, String) -> Unit
    ): List<UserProfile> {
        val potentials = mutableListOf<UserProfile>()

        val userAge = if(userData.age.isNullOrEmpty()) 0 else userData.age.toInt()

        val minAge = userAge - 5
        val maxAge = userAge + 5
//        val query = firestore.collection(COLLECTION_PROFILES)
//            .whereEqualTo("gender", if (userData.gender == "Male") "Female" else "Male")
//            .whereGreaterThanOrEqualTo("age", minAge)
//            .whereLessThanOrEqualTo("age", maxAge)
//            .limit(5)
        val cardsQuery =
            when (userData.gender) {
                "Male" -> firestore.collection(COLLECTION_PROFILES)
                    .whereEqualTo("gender", "Female")

                "Female" -> firestore.collection(COLLECTION_PROFILES)
                    .whereEqualTo("gender", "Male")

                else -> firestore.collection(COLLECTION_PROFILES)

            }

        val aged = cardsQuery.where(
            Filter.or(
                Filter.greaterThanOrEqualTo("age","$minAge"),
                Filter.lessThanOrEqualTo("age","$maxAge")

            )
        )


        val filteredArtlangs =
            aged
            .whereArrayContainsAny("favLanguages", if(userData.favLanguages.isNullOrEmpty()) listOf("") else userData.favLanguages)
            .whereArrayContainsAny("favArtists",if(userData.favArtists.isNullOrEmpty()) listOf(ProfileArtist()) else userData.favArtists)


            filteredArtlangs
                .addSnapshotListener {

                    value, error ->
                if (error != null) {

                    handleException(error, "Failed to get profiles, try again")
                    Log.d("Profiles", "populateCards: $error")
                }
                value?.documents?.forEach {
                    it.toObject<UserProfile>()?.let { potential ->
                        Log.d("Profile", "populateCards: $potential")
                        var showUser = true
                        if (userData.swipesLeft.contains(potential.uid)
                            || userData.swipesRight.contains(potential.uid)
                            || userData.matches.contains(potential.uid)
                        )
                            showUser = false
                        if (showUser)
                            potentials.add(potential)
                        Log.d("Profiles", "populateCards: $potentials")
                        handleSuccess(potentials, "Got some profiles ")
                    }
                }
            }
        return potentials
    }


}