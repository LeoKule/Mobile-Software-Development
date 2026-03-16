package com.kulev.myapp

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthProvider {
    fun getAuth(context: Context): FirebaseAuth {
        val app = FirebaseInitializer.ensureInitialized(context.applicationContext)
        return FirebaseAuth.getInstance(app)
    }
}