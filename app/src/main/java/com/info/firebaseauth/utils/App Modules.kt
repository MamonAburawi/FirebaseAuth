package com.info.firebaseauth.utils

import com.info.firebaseauth.screens.authentication.AuthViewModel
import com.info.firebaseauth.remote.RemoteAuth
import com.info.firebaseauth.repository.AuthRepository
import org.koin.dsl.module


val appModule = module {

    single { RemoteAuth() }
    single { AuthRepository(remoteAuth = get()) }
    single { AuthViewModel(authRepository = get()) }

}