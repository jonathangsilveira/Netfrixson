package br.edu.jgsilveira.portfolio.netfrixson.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.viewmodel.MovieViewModel

class MovieActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this).get(MovieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        viewModel.processing.observe(this, processObserver())
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else
            super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        supportFragmentManager?.findFragmentById(R.id.contentMovie)?.apply {
            when (tag) {
                MovieFragment.TAG -> moveTaskToBack(true)
                LoadingFragment.TAG -> {  }
                else -> { super.onBackPressed() }
            }
        }
    }

    private fun init() {
        val movieId = intent?.getIntExtra("movieId", 0)
        supportFragmentManager?.beginTransaction()
                ?.replace(R.id.contentMovie, MovieFragment.newInstance(movieId))
                ?.addToBackStack(null)
                ?.commit()
    }

    private fun processObserver() = Observer<Boolean> { processing ->
        if (processing == true)
            supportFragmentManager
                    ?.beginTransaction()
                    ?.add(R.id.contentMovie, LoadingFragment())
                    ?.addToBackStack(MovieFragment.TAG)
                    ?.commit()
        else
            supportFragmentManager?.popBackStack()
    }

}
