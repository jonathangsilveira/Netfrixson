package br.edu.jgsilveira.portfolio.netfrixson.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies
import kotlinx.android.synthetic.main.rated_movies_item.view.*

class DiscoverMoviesAdapter(val onItemClickListener: (Int) -> Unit) :
        RecyclerView.Adapter<DiscoverMoviesAdapter.MovieViewHolder>() {

    private var _data: DiscoverMovies? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rated_movies_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = _data?.results?.size ?: 0

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        _data?.results?.let { list ->
            val item = list[position]
            holder.movieId = item.id
            holder.itemView.titleRatedMovies?.text = item.title
            holder.itemView.averageRatedMovies?.text = "Vote average: ${item.voteAverage}"
        }
    }

    fun setData(data: DiscoverMovies?) {
        _data = data
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var movieId: Int = 0

        init {
            itemView.setOnClickListener { onItemClickListener(movieId) }
        }

    }

}