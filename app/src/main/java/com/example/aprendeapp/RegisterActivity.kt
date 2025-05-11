package com.example.aprendeapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aprendeapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val email    = binding.etEmailReg.text.toString().trim()
            val pass1    = binding.etPasswordReg.text.toString().trim()
            val pass2    = binding.etPasswordConfirm.text.toString().trim()

            if (email.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                Toast.makeText(this,
                    getString(R.string.error_empty_fields),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (pass1 != pass2) {
                Toast.makeText(this,
                    getString(R.string.error_password_mismatch),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, pass1)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,
                            getString(R.string.success_register),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(this,
                            task.exception?.localizedMessage
                                ?: getString(R.string.error_register_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
