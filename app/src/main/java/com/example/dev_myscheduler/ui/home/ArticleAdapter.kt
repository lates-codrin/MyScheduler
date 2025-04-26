package com.example.dev_myscheduler.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dev_myscheduler.R

class NewsAdapter(
    private val articles: List<Article>,
    private val onArticleClick: (String) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.articleTitle)
        val date: TextView = view.findViewById(R.id.articleDate)
        val desc: TextView = view.findViewById(R.id.articleDescription)
        val image: ImageView = view.findViewById(R.id.articleImage)
        val button: Button = view.findViewById(R.id.readMoreButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.articleTitle.take(100) + "..."
        holder.date.text = article.articleDate
        holder.desc.text = article.articleDescription.take(150) + " [...]"

        if (!article.articleImage.isNullOrEmpty()) {
            holder.image.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(article.articleImage)
                .into(holder.image)
        }


        holder.button.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Open article?")
                .setMessage("Would you like to open this link in your browser?")
                .setPositiveButton("Da") { _, _ -> onArticleClick(article.articleLink) }
                .setNegativeButton("Nu", null)
                .show()
        }
    }

    override fun getItemCount(): Int = articles.size
}
