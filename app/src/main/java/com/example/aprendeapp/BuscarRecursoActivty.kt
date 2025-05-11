package com.example.aprendeapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.aprendeapp.databinding.ActivityBuscarRecursoBinding
import com.example.aprendeapp.model.Recurso
import com.example.aprendeapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuscarRecursoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuscarRecursoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscarRecursoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscarId.setOnClickListener {
            val id = binding.etId.text.toString().toIntOrNull()
            if (id == null) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            buscarRecurso(id)
        }
    }

    private fun buscarRecurso(id: Int) {
        RetrofitClient.apiService.getById(id).enqueue(object: Callback<Recurso> {
            override fun onResponse(call: Call<Recurso>, resp: Response<Recurso>) {
                if (resp.isSuccessful && resp.body() != null) {
                    mostrarResultado(resp.body()!!)
                } else {
                    Toast.makeText(this@BuscarRecursoActivity,
                        "Recurso no encontrado (HTTP ${resp.code()})",
                        Toast.LENGTH_SHORT).show()
                    binding.cardResult.visibility = View.GONE
                }
            }
            override fun onFailure(call: Call<Recurso>, t: Throwable) {
                Toast.makeText(this@BuscarRecursoActivity,
                    "Error de red: ${t.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
                binding.cardResult.visibility = View.GONE
            }
        })
    }

    private fun mostrarResultado(r: Recurso) {
        binding.cardResult.visibility = View.VISIBLE
        binding.tvTituloRes.text       = r.titulo
        binding.tvTipoRes.text         = r.tipo
        binding.tvDescripcionRes.text  = r.descripcion
        binding.tvEnlaceRes.text       = r.enlace
        // Cargar imagen con Glide
        Glide.with(this)
            .load(r.imagen)
            .placeholder(android.R.color.darker_gray)               // color de fondo
            .error(android.R.drawable.stat_notify_error)             // icono de error
            .into(binding.ivImagen)

    }
}
