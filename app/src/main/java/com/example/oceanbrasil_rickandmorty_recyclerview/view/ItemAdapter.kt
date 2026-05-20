package com.example.oceanbrasil_rickandmorty_recyclerview.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.oceanbrasil_rickandmorty_recyclerview.R
import com.example.oceanbrasil_rickandmorty_recyclerview.domain.Item

class ItemAdapter(var itens: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Item) {
            val textView = itemView.findViewById<TextView>(R.id.textView)
            val imageView = itemView.findViewById<ImageView>(R.id.imageView)
            val description = itemView.findViewById<TextView>(R.id.descriptionView)
            val specie = itemView.findViewById<TextView>(R.id.tvSpecies)
            val status = itemView.findViewById<TextView>(R.id.tvStatus)

            textView.text = item.nome
            // Na API real, não temos o campo "description" direto no Item da lista da mesma forma que o mock,
            // mas podemos mostrar o gênero ou local se desejar. Por enquanto usaremos o gender.
            description.text = "Gênero: ${item.gender}"
            specie.text = "Espécie: ${item.species}"
            status.text = "Status: ${item.status}"

            // --- Diagnóstico Glide: imprime a URL recebida do JSON ---
            Log.d("GlideDebug", "load() para '${item.nome}' (id=${item.id}) -> \"${item.imagem}\"")

            Glide.with(imageView.context)
                .load(item.imagem)
                // Aumenta o timeout do HttpUrlFetcher: default é 2500ms, curto demais
                // para o emulador. 15s dá folga para o servidor responder.
                .apply(RequestOptions().timeout(15_000))
                // enquanto carrega, mostra um ícone padrão (assim você vê que o Glide está vivo)
                .placeholder(android.R.drawable.ic_menu_gallery)
                // se falhar, mostra um ícone de erro em vez de continuar cinza silenciosamente
                .error(android.R.drawable.stat_notify_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("GlideDebug", "FALHA ao carregar $model: ${e?.message}", e)
                        // imprime a cadeia completa de exceções no Logcat (rede, SSL, decode...)
                        e?.logRootCauses("GlideDebug")
                        return false // false = deixa o Glide exibir o .error()
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("GlideDebug", "OK '$model' (fonte=$dataSource)")
                        return false // false = deixa o Glide desenhar o resultado
                    }
                })
                .into(imageView)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ItemDetailActivity::class.java)
                intent.putExtra("ID", item.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itens.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itens[position]
        holder.bindView(item)
    }

    fun updateData(newItens: List<Item>) {
        this.itens = newItens
        notifyDataSetChanged()
    }
}