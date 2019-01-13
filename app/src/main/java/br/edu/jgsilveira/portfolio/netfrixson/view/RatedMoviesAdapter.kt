package br.edu.jgsilveira.portfolio.netfrixson.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.RatedMovies

class RatedMoviesAdapter : RecyclerView.Adapter<RatedMoviesAdapter.RatedMoviesViewHolder>() {

    var data: RatedMovies? = null
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RatedMoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rated_movies_item, parent, false)
        return RatedMoviesViewHolder(view)
    }

    override fun getItemCount(): Int = data?.results?.size ?: 0

    override fun onBindViewHolder(holder: RatedMoviesViewHolder, position: Int) {
        data?.results?.let { list ->
            val item = list[position]
            val view = holder.itemView as TextView
            view.text = "${item.title}, ${item.releaseDate} - Rate: ${item.rating}"
        }
    }

    class RatedMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}