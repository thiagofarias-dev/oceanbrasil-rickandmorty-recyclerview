package com.example.oceanbrasil_rickandmorty_recyclerview.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oceanbrasil_rickandmorty_recyclerview.R
import com.example.oceanbrasil_rickandmorty_recyclerview.domain.Personagem

class ItemAdapter (val itens: List<Personagem>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Personagem) {
            val textView = itemView.findViewById<TextView>(R.id.textView)
            val imageView = itemView.findViewById<ImageView>(R.id.imageView)
            val descriptionView = itemView.findViewById<TextView>(R.id.descriptionView)
            val textSpecie = itemView.findViewById<TextView>(R.id.textSpecie)
            textView.text = item.nome
            descriptionView.text = item.description
            textSpecie.text = item.specie

            // Glide funciona perfeitamente com IDs de drawable (Int)
            Glide.with(imageView).load(item.imagem).into(imageView)

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
}