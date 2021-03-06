package com.info.firebaseauth.repository

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.info.firebaseauth.remote.RemoteAuth
import com.info.firebaseauth.data.User

class AuthRepository(private val remoteAuth: RemoteAuth) {

    suspend fun signInWithEmail(email: String,
                                password: String,
                                onSuccess:(Task<AuthResult>)-> Unit,
                                onError:(String)-> Unit) = remoteAuth.signIn(email, password,onSuccess, onError)

    suspend fun signUp(user: User,
                       onSuccess:(Task<AuthResult>)-> Unit,
                       onError:(Exception)-> Unit) = remoteAuth.singUp(user,onSuccess, onError)

    suspend fun signOut() = remoteAuth.signOut()


    suspend fun resetPassword(email: String,
                              onSuccess:( Task<Void>)-> Unit,
                              onError:(String)-> Unit) = remoteAuth.resetPassword(email, onSuccess, onError)

    suspend fun signInWithPhone(activity: Activity,
                                credential: PhoneAuthCredential,
                                onSuccess:(Task<AuthResult>)-> Unit,
                                onError:(String)-> Unit) = remoteAuth.signInWithPhoneAuthCredential(activity,credential, onSuccess, onError)

}