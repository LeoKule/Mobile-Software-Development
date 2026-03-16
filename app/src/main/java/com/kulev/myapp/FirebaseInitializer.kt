package com.kulev.myapp

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

object FirebaseInitializer {

    private const val TAG = "FirebaseInitializer"

    fun ensureInitialized(context: Context): FirebaseApp {
        FirebaseApp.getApps(context).firstOrNull()?.let { return it }

        FirebaseApp.initializeApp(context)?.let { return it }

        val options = FirebaseOptions.Builder()
            .setApiKey("AIzaSyBu9YFAdrNc6Jh9UxFUSV3lhTVyw1S2oOU")
            .setApplicationId("1:371808192745:android:6021ca3500a268e7ce8878")
            .setProjectId("phonerating-ff052")
            .setStorageBucket("phonerating-ff052.firebasestorage.app")
            .setGcmSenderId("371808192745")
            .build()

        Log.w(TAG, "Automatic Firebase initialization was unavailable; using explicit FirebaseOptions fallback.")
        return FirebaseApp.initializeApp(context, options)
            ?: error("Firebase initialization failed")
    }
}