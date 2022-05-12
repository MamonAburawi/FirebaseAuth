package com.info.firebaseauth.data

import java.util.*

data class User(
    val id: String = "",
    val email: String = "",
    val password: String = "",
    val date: Date = Date()
)
