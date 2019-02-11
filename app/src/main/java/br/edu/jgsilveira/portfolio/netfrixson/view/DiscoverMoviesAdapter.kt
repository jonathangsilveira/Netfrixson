package br.edu.jgsilveira.portfolio.netfrixson.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies

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
            val view = holder.itemView as TextView
            holder.movieId = item.id
            view.text = "${item.title}, ${item.releaseDate} - Vote average: ${item.voteAverage}"
        }
    }

    fun setData(data: DiscoverMovies?) {
        _data = data
        notifyDataSetChanged()
    }

    interface OnItemClickListener {

        fun onItemClicked(movieId: Int)

    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var movieId: Int = 0

        init {
            itemView.setOnClickListener { onItemClickListener(movieId) }
        }

    }

}