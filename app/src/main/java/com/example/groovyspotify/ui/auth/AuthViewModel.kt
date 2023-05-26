package com.example.groovyspotify.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovyspotify.data.emailAuth.AuthRepository
import com.example.groovyspotify.data.utils.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

//    private val _state = MutableStateFlow(SignInState(false, null))
//    val state = _state.asStateFlow()

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow



    private val _googleState = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val googleState: StateFlow<Resource<FirebaseUser>?> = _googleState


    val currentUser: FirebaseUser?
        get() = repository.currentUser


    init {
        if(repository.currentUser != null){
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
        if(repository.currentUser != null){
            _googleState.value = Resource.Success(repository.currentUser!!)
        }

    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(email = email, password = password)
        _loginFlow.value = result
    }

    fun signup(name: String, email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = Resource.Loading
        val result = repository.signup(name = name, email = email, password = password)
        _signupFlow.value = result
    }

    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        _googleState.value = Resource.Loading
        val result = repository.googleSignIn(credential)
        _googleState.value = result
    }


    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
        _googleState.value = null
    }


}