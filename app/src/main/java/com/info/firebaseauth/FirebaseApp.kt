package com.info.firebaseauth

import android.app.Application

class FirebaseApp: Application(){

    override fun onCreate() {
        super.onCreate()


        

//        startKoin {
//            androidContext(this@ShoppingApplication)
//
////			loadKoinModules(
////				module(override = true){ userLiveDataModule} ,
////			)
//
//            modules(listOf(
//                productLiveDataModule,
//                viewModelsModules,
//                userRepositoryModule,
//                productRepositoryModule,
//                localDataBase,
//            ))
//        }

    }
}