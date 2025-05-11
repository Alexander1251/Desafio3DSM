package com.example.aprendeapp.model

import java.io.Serializable

data class Recurso(
    val id: Int? = null,
    val titulo: String,
    val descripcion: String,
    val tipo: String,
    val enlace: String,
    val imagen: String
) : Serializable
