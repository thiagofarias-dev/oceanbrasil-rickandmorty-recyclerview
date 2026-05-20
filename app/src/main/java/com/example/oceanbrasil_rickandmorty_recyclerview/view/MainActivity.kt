package com.example.oceanbrasil_rickandmorty_recyclerview.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oceanbrasil_rickandmorty_recyclerview.R
import com.example.oceanbrasil_rickandmorty_recyclerview.api.ApiRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

        // Configuração da RecyclerView
        val rvItens = findViewById<RecyclerView>(R.id.rvItens)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        rvItens.layoutManager = LinearLayoutManager(this)

        // Inicializa o adapter com uma lista vazia
        val adapter = ItemAdapter(emptyList())
        rvItens.adapter = adapter

        // Observa os dados do repositório (API)
        ApiRepository.itens.observe(this) { listaDePersonagens ->
            // Quando os dados chegarem da API, atualizamos o adapter
            adapter.updateData(listaDePersonagens)
        }

        // Observa o estado de carregamento para mostrar/esconder o ProgressBar
        ApiRepository.isLoading.observe(this) { loading ->
            if (loading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

        // Botão de Retry
        val btnRetry = findViewById<FloatingActionButton>(R.id.btnRetry)
        btnRetry.setOnClickListener {
            Toast.makeText(this, "Atualizando dados...", Toast.LENGTH_SHORT).show()
            ApiRepository.fetchItems()
        }
    }
}