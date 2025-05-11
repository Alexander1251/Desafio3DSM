package com.example.aprendeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aprendeapp.Constants.EXTRA_RECURSO
import com.example.aprendeapp.databinding.ActivityListarRecursosBinding
import com.example.aprendeapp.model.Recurso
import com.example.aprendeapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListarRecursosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListarRecursosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarRecursosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvRecursos.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        // Cada vez que la Activity vuelve a primer plano, recargamos la lista
        cargarLista()
    }

    private fun cargarLista() {
        RetrofitClient.apiService.getAll().enqueue(object : Callback<List<Recurso>> {
            override fun onResponse(call: Call<List<Recurso>>, resp: Response<List<Recurso>>) {
                Log.d("ListarRecursos", "HTTP ${resp.code()} – body=${resp.body()}")
                if (resp.isSuccessful) {
                    val lista = resp.body() ?: emptyList()
                    binding.rvRecursos.adapter = ResourceAdapter(lista) { recurso ->
                        // Lanzamos la edición sin manejar resultado manual
                        val intent = Intent(this@ListarRecursosActivity,
                            EditarRecursoActivity::class.java)
                        intent.putExtra(EXTRA_RECURSO, recurso)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@ListarRecursosActivity,
                        "Error HTTP ${resp.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Recurso>>, t: Throwable) {
                Log.e("ListarRecursos", "Fallo red", t)
                Toast.makeText(this@ListarRecursosActivity,
                    "Fallo de red: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
