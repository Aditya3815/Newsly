package com.example.newsly.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsly.NewsDetailsActivity
import com.example.newsly.R
import com.example.newsly.models.Article

class NewsAdapter(private val context: Context, private val newsList: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.findViewById<ImageView>(R.id.newsImage).apply {
                Glide.with(context)
                    .load(newsList[adapterPosition].urlToImage)
                    .into(this)
            }
            itemView.findViewById<TextView>(R.id.newsTitle).text = newsList[adapterPosition].title
            itemView.findViewById<TextView>(R.id.newsDescription).text =
                newsList[adapterPosition].description

            itemView.setOnClickListener {
                val intent = Intent(context, NewsDetailsActivity::class.java)
                intent.putExtra("url", newsList[adapterPosition].url)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) = holder.bind()

}