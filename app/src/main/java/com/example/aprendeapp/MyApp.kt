package com.example.aprendeapp

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Fuerza la carga de tu configuración de Firebase
        FirebaseApp.initializeApp(this)
        // Debug: comprueba en Logcat que Firebase sí se inicializa
        Log.d("MyApp", "Firebase initialized: ${FirebaseApp.getApps(this)}")
    }
}
