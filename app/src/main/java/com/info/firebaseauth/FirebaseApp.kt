package com.info.firebaseauth

import android.app.Application
import com.info.firebaseauth.utils.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FirebaseApp: Application(){

    override fun onCreate() {
        super.onCreate()


        startKoin{
            androidContext(this@FirebaseApp)
            modules(listOf(appModule))
        }



    }
}