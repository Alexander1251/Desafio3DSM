package com.example.aprendeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aprendeapp.databinding.ItemRecursoBinding
import com.example.aprendeapp.model.Recurso

class ResourceAdapter(
    private val list: List<Recurso>,
    private val onClick: (Recurso) -> Unit
) : RecyclerView.Adapter<ResourceAdapter.VH>() {

    inner class VH(private val binding: ItemRecursoBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(r: Recurso) {
            // Título y tipo
            binding.tvTitulo.text = r.titulo
            binding.tvTipo.text   = r.tipo

            // Carga la imagen con Glide
            Glide.with(binding.ivRecurso.context)
                .load(r.imagen)
                .placeholder(android.R.color.darker_gray)
                .error(android.R.drawable.stat_notify_error)
                .into(binding.ivRecurso)

            // Click al ítem entero
            binding.root.setOnClickListener { onClick(r) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemRecursoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(list[position])

    override fun getItemCount() = list.size
}
