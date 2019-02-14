package br.edu.jgsilveira.portfolio.netfrixson.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.viewmodel.MovieViewModel

class MovieActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this).get(MovieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        viewModel.processing.observe(this, processObserver())
        viewModel.error.observe(this, errorObserver())
        viewModel.movie.observe(this, detailsObserver())
        val movieId = intent?.getIntExtra("movieId", 0)
        movieId?.let { viewModel.query(movieId) }
    }

    private fun processObserver() = Observer<Boolean> { processing ->

    }

    private fun errorObserver() = Observer<String> { error ->
        AlertDialog.Builder(this).apply {
            setCancelable(false)
            setTitle(R.string.error)
            setMessage(error)
            setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    private fun detailsObserver() = Observer<Movie> { movie ->
        movie?.let { println(movie) }
    }

}
