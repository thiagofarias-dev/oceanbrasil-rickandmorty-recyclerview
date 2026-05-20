package com.example.oceanbrasil_rickandmorty_recyclerview.domain


import com.google.gson.annotations.SerializedName


data class Item(
    val id: Int,
    @SerializedName("name")
    val nome: String,
    @SerializedName("image")
    val imagem: String,
    val status: String,
    val species: String,
    val gender: String,
    val url: String
)