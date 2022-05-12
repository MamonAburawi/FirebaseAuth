package com.info.firebaseauth.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.firebaseauth.data.User
import com.info.firebaseauth.remote.RemoteAuth
import com.info.firebaseauth.repository.AuthRepository
import kotlinx.coroutines.launch
import java.util.*

class AuthViewModel : ViewModel() {
    companion object{
        const val TAG = "AuthViewModel"
    }

    private val authRepository = AuthRepository(RemoteAuth())

    private var _isRegister = MutableLiveData<User?>()
    val isRegister: LiveData<User?> = _isRegister

    private var _isLogin = MutableLiveData<Boolean?>()
    val isLogin: LiveData<Boolean?> = _isLogin

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress



    fun signUp(email: String, password: String) {
        _progress.value = true
        resetError()
        viewModelScope.launch {
            val newId = UUID.randomUUID().toString()
            val user = User(newId,email,password)
            authRepository.signUp( user,
            onSuccess = {
                Log.d(TAG,"SignUp Success: ${it.result}")
                _isRegister.value = user
                _progress.value = false
            },
            onError = {
                Log.d(TAG,"SignUp Error: ${it.message}")
                _error.value = it.message
                resetAuth()
            })
        }
    }


    fun signIn(email: String, password: String) {
        _progress.value = true
        resetError()
        viewModelScope.launch {
            authRepository.signIn(email, password,
            onSuccess = {
                Log.d(TAG,"Login Success: ${it.result}")
                _isLogin.value = true
                _progress.value = false
            },
            onError = { message ->
                Log.d(TAG,"Login Error: $message")
                _error.value = message
                resetAuth()
            })

        }
    }



    private fun resetError(){
        _error.value = null
    }



    private fun resetAuth() {
        _isLogin.value = null
        _progress.value = false
        _isRegister.value = null
    }






}