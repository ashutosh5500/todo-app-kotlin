package com.ashu.test

import android.util.Log
//import javax.inject.Inject

class UserRepository {
    fun saveUser(email: String, password: String) {
        Log.d("UserRepository", "Saving user with email: $email and password: $password")
    }

}