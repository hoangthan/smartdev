package com.smartdev.features.movie.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.smartdev.features.core.R
import com.smartdev.features.movie.databinding.ItemMovieBinding
import com.smartdev.features.movie.movielist.MovieListAdapter.MovieItemViewHolder

val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieUi>() {
    override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
        return oldItem.imdbID == newItem.imdbID
    }

    override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
        return oldItem == newItem
    }
}

class MovieListAdapter : PagingDataAdapter<MovieUi, MovieItemViewHolder>(MOVIE_COMPARATOR) {

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        if (!::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }

        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class MovieItemViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(movie: MovieUi?) {
            movie ?: return

            binding.imgMovie.load(movie.poster) {
                crossfade(true)
                crossfade(200)
                error(R.drawable.ic_download_fail)
                placeholder(R.drawable.ic_loading)
            }

            binding.tvMovieYear.text = movie.year
            binding.tvMovieName.text = movie.title
        }
    }
}
