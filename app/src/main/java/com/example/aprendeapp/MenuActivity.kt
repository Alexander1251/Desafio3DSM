package com.example.aprendeapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aprendeapp.databinding.ActivityMenuBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnListar.setOnClickListener {
            startActivity(Intent(this, ListarRecursosActivity::class.java))
        }
        binding.btnBuscar.setOnClickListener {
            startActivity(Intent(this, BuscarRecursoActivity::class.java))
        }
        binding.btnAgregar.setOnClickListener {
            startActivity(Intent(this, AgregarRecursoActivity::class.java))
        }
        binding.btnCerrar.setOnClickListener {
            Firebase.auth.signOut()
            finish() // Vuelve al Login
        }
    }
}
