package com.example.aprendeapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aprendeapp.databinding.ActivityAgregarRecursoBinding
import com.example.aprendeapp.model.Recurso
import com.example.aprendeapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarRecursoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarRecursoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarRecursoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {
            val r = Recurso(
                titulo     = binding.etTitulo.text.toString(),
                descripcion= binding.etDescripcion.text.toString(),
                tipo       = binding.etTipo.text.toString(),
                enlace     = binding.etEnlace.text.toString(),
                imagen     = binding.etImagen.text.toString()
            )
            RetrofitClient.apiService.create(r).enqueue(object: Callback<Recurso> {
                override fun onResponse(call: Call<Recurso>, resp: Response<Recurso>) {
                    if (resp.isSuccessful) {
                        Toast.makeText(this@AgregarRecursoActivity,
                            "Creado con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AgregarRecursoActivity,
                            "Error al crear", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Recurso>, t: Throwable) {
                    Toast.makeText(this@AgregarRecursoActivity,
                        t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
