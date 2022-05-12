package com.info.firebaseauth.remote


import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.info.firebaseauth.utils.Result
import com.info.firebaseauth.data.User
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

@Suppress("UNREACHABLE_CODE")
class RemoteAuth() {

    private  val _auth = FirebaseAuth.getInstance()
    private  val _rootStore = FirebaseFirestore.getInstance()
    private val usersCollection = _rootStore.collection(USERS_COLLECTION)



    private suspend fun signInWithEmailAndPassword(email: String,
                                                    password: String,
                                                    onSuccess:(Task<AuthResult>)-> Unit,
                                                    onError:(String)-> Unit) {
         return supervisorScope {
             val task = async {
                 _auth.signInWithEmailAndPassword(email, password)
                     .addOnCompleteListener {
                         if (it.isSuccessful){
                             if (it.result.user!!.isEmailVerified){
                                 Log.d(TAG,"Login Success: ${it.result}")
                                 onSuccess(it)
                             }else{
                                 val error = "email is not verified!"
                                 Log.d(TAG,"Login Error: $error")
                                 onError(error)
                             }
                         }
                     }
                     .addOnFailureListener {
                         Log.d(TAG,"Login Error: ${it.message}")
                         onError(it.message.toString())
                     }
             }
             try {
                 task.await()
             }catch (ex: Exception){
                 Log.d(TAG,"Login Error: ${ex.message}")
                 onError(ex.message.toString())
             }
         }
    }



    suspend fun signIn(email: String,
                       password: String,
                       onSuccess:(Task<AuthResult>)-> Unit,
                       onError:(String)-> Unit) =
        signInWithEmailAndPassword(email, password,onSuccess, onError)



    suspend fun singUp(user: User,
                       onSuccess:(Task<AuthResult>)-> Unit,
                       onError:(Exception)-> Unit) {
        return supervisorScope {

           val task = async {
               _auth.createUserWithEmailAndPassword(user.email, user.password)
                   .addOnCompleteListener {
                   if (it.isSuccessful){
                       it.result.user?.sendEmailVerification()?.addOnCompleteListener { sendVerifyTask ->
                           Log.d(TAG,"Registration Success: ${it.result}")
                           onSuccess(it)
                           addUser(user)
                       }
                   }
               }
                   .addOnFailureListener {
                       onError(it)
                       Log.d(TAG,"Registration Failed: message: ${it.message}" )
                   }
           }
            try {
                task.await()
            }catch (ex: Exception){
                onError(ex)
                Log.d(TAG,"Registration Failed: message: ${ex.message}" )
            }
        }
    }



    private fun addUser(user: User) {
        try {
            usersCollection.document(user.id).set(user)
                .addOnCompleteListener {
                    Log.d(TAG,"adding User Success")
                }
                .addOnFailureListener {
                    Log.d(TAG,"adding User Failed: message: ${it.message} \n cause: ${it.cause}" )
                }
        }catch (ex: Exception){
            Log.d(TAG,"Adding User Failed: message: ${ex.message} \n cause: ${ex.cause}" )
            Result.Error(ex)
        }
    }


    suspend fun signOut(): Result<Boolean> {
        return supervisorScope {
            val remoteRes = async { _auth.signOut() }
            try {
                remoteRes.await()
                Result.Success(true)
            }catch (ex: Exception){
                Result.Error(ex)
            }
        }
    }



    companion object{
        private const val USERS_COLLECTION = "users"
        private const val TAG = "RemoteAuth"
    }



}