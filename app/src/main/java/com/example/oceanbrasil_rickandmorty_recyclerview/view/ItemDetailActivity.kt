package com.example.oceanbrasil_rickandmorty_recyclerview.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.oceanbrasil_rickandmorty_recyclerview.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.oceanbrasil_rickandmorty_recyclerview.api.ApiService
import com.example.oceanbrasil_rickandmorty_recyclerview.domain.ItemDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        val id = intent.getIntExtra("ID", 0)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val apiService = retrofit.create(ApiService::class.java)

        apiService.getCharacterById(id).enqueue(object : Callback<ItemDetail> {

            override fun onResponse(call: Call<ItemDetail>, response: Response<ItemDetail>) {
                response.body()?.let {
                    Log.d("API", it.toString()) // Log para fins de depuração.

                    val tvNome = findViewById<TextView>(R.id.tvNome)
                    val ivImagem = findViewById<ImageView>(R.id.ivImagem)
                    val tvOrigin = findViewById<TextView>(R.id.tvOrigin)

                    tvNome.text = it.name
                    tvOrigin.text = it.origin.name
                    // .timeout(15_000) evita SocketTimeoutException em redes lentas / emulador.
                    Glide.with(ivImagem)
                        .load(it.image)
                        .apply(RequestOptions().timeout(15_000))
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.stat_notify_error)
                        .into(ivImagem)
                }
            }

            // 10. `onFailure` é executado se ocorrer um erro de rede.
            override fun onFailure(call: Call<ItemDetail>, t: Throwable) {
                // Registra o erro no Logcat para análise do desenvolvedor.
                Log.e("API", "Erro ao carregar dados da API.", t)
            }
        })
    }
}