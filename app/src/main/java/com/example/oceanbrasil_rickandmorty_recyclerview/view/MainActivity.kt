package com.example.oceanbrasil_rickandmorty_recyclerview.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oceanbrasil_rickandmorty_recyclerview.R
import com.example.oceanbrasil_rickandmorty_recyclerview.domain.Personagem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // configuração da recyclerview
        val rvItens = findViewById<RecyclerView>(R.id.rvItens)

        //criar a lista de dados locais
        val personagens = listOf(
            Personagem(
                nome = "Rick Sanchez",
                imagem = android.R.drawable.ic_dialog_info,
                description = "Descrição do Rick Sanchez",
                specie = "Humano"
            ),
            Personagem(
                nome = "Morty Smith",
                imagem = android.R.drawable.ic_dialog_alert,
                description = "Descrição do Morty Smith",
                specie = "Humano"
            ),
        Personagem(
            nome = "Summer Smith",
            imagem = android.R.drawable.ic_dialog_dialer,
            description = "Descrição do Summer Smith",
            specie = "Humano"
        )
    )
        // Criar e definir o layout manager
        rvItens.layoutManager = LinearLayoutManager(this)
        // Criar e configurar o adapter
        rvItens.adapter = ItemAdapter(personagens)
    }
}