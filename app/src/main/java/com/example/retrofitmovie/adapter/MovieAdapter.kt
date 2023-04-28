package com.example.retrofitmovie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.retrofitmovie.R
import com.example.retrofitmovie.response.MovieListResponse
import com.example.retrofitmovie.ui.DetailsActivity
import com.example.retrofitmovie.utils.Constant
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(/*private val movie: ArrayList<Movie>*/) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        context = parent.context
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MovieListResponse.Result) {
            with(itemView) {
                tvName.text = item.title
                tvRating.text = item.vote_average.toString()
                val moviePosterURL = Constant.POSTER_BASE_URL + item?.poster_path
                ivPoster.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.ss_1)
                    //scale(Scale.FILL)
                }
                tvLang.text = item.original_language
                tvRelease.text = item.release_date

                setOnClickListener {
                    val intent = Intent(context,DetailsActivity::class.java)
                    intent.putExtra("id", item.id)
                    context.startActivity(intent)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<MovieListResponse.Result>() {
        override fun areItemsTheSame(
            oldItem: MovieListResponse.Result,
            newItem: MovieListResponse.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieListResponse.Result,
            newItem: MovieListResponse.Result
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}