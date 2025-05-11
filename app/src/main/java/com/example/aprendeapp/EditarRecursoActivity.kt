// File: app/src/main/java/com/example/aprendeapp/EditarRecursoActivity.kt
package com.example.aprendeapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aprendeapp.Constants.EXTRA_RECURSO
import com.example.aprendeapp.databinding.ActivityEditarRecursoBinding
import com.example.aprendeapp.model.Recurso
import com.example.aprendeapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarRecursoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarRecursoBinding
    private lateinit var recurso: Recurso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarRecursoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recurso = intent.getSerializableExtra(EXTRA_RECURSO) as Recurso

        // Pre-llenar campos
        binding.etTitulo.setText(recurso.titulo)
        binding.etDescripcion.setText(recurso.descripcion)
        binding.etTipo.setText(recurso.tipo)
        binding.etEnlace.setText(recurso.enlace)
        binding.etImagen.setText(recurso.imagen)

        binding.btnActualizar.setOnClickListener {
            val id = recurso.id ?: run {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
                finish()
                return@setOnClickListener
            }
            val actualizado = recurso.copy(
                titulo      = binding.etTitulo.text.toString(),
                descripcion = binding.etDescripcion.text.toString(),
                tipo        = binding.etTipo.text.toString(),
                enlace      = binding.etEnlace.text.toString(),
                imagen      = binding.etImagen.text.toString()
            )

            RetrofitClient.apiService.update(id, actualizado)
                .enqueue(object: Callback<Recurso> {
                    override fun onResponse(call: Call<Recurso>, resp: Response<Recurso>) {
                        Log.d("EditarRecurso", "HTTP ${resp.code()} – body=${resp.body()}")
                        if (resp.isSuccessful) {
                            Toast.makeText(
                                this@EditarRecursoActivity,
                                "Actualizado con éxito",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Señalar cambio para refrescar la lista
                            setResult(Activity.RESULT_OK)
                            finish()
                        } else {
                            Toast.makeText(
                                this@EditarRecursoActivity,
                                "Error al actualizar: HTTP ${resp.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    override fun onFailure(call: Call<Recurso>, t: Throwable) {
                        Log.e("EditarRecurso", "Fallo red", t)
                        Toast.makeText(
                            this@EditarRecursoActivity,
                            "Fallo de red: ${t.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }

        binding.btnEliminar.setOnClickListener {
            val id = recurso.id ?: return@setOnClickListener
            RetrofitClient.apiService.delete(id)
                .enqueue(object: Callback<Void> {
                    override fun onResponse(call: Call<Void>, resp: Response<Void>) {
                        if (resp.isSuccessful) {
                            Toast.makeText(
                                this@EditarRecursoActivity,
                                "Eliminado con éxito",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Señalar cambio para refrescar la lista
                            setResult(Activity.RESULT_OK)
                            finish()
                        } else {
                            Toast.makeText(
                                this@EditarRecursoActivity,
                                "Error al eliminar: HTTP ${resp.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(
                            this@EditarRecursoActivity,
                            "Fallo de red: ${t.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}
