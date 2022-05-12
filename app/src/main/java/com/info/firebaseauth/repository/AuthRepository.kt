package com.info.firebaseauth.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.info.firebaseauth.remote.RemoteAuth
import com.info.firebaseauth.data.User

class AuthRepository(private val remoteAuth: RemoteAuth) {

    suspend fun signIn(email: String,
                       password: String,
                       onSuccess:(Task<AuthResult>)-> Unit,
                       onError:(String)-> Unit) = remoteAuth.signIn(email, password,onSuccess, onError)

    suspend fun signUp(user: User,
                       onSuccess:(Task<AuthResult>)-> Unit,
                       onError:(Exception)-> Unit) = remoteAuth.singUp(user,onSuccess, onError)

    suspend fun signOut() = remoteAuth.signOut()

}