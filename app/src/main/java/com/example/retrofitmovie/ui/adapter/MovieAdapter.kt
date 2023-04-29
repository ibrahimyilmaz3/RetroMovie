package com.example.retrofitmovie.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.retrofitmovie.R
import com.example.retrofitmovie.remote.model.MovieList
import com.example.retrofitmovie.ui.activity.DetailsActivity
import com.example.retrofitmovie.utils.Constant
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val itemList: List<MovieList.Result>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MovieList.Result) {
            with(itemView) {
                tvName.text = item.title
                tvRating.text = item.vote_average.toString()
                val moviePosterURL = Constant.POSTER_BASE_URL + item.poster_path
                ivPoster.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.ss_1)
                    scale(Scale.FILL)
                }
                tvLang.text = item.original_language
                tvRelease.text = item.release_date

                setOnClickListener {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("id", item.id)
                    context.startActivity(intent)
                }
            }
        }
    }

}