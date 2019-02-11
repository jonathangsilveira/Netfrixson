package br.edu.jgsilveira.portfolio.netfrixson.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.viewmodel.DiscoverViewModel

class DiscoverActivity : AppCompatActivity(), IActivityFragment {

    private lateinit var list: RecyclerView

    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: DiscoverViewModel

    private var moviesAdapter: DiscoverMoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)
        viewModel = ViewModelProviders.of(this).get(DiscoverViewModel::class.java)
        setupRecyclerView()
        bindData()
    }

    override fun onResume() {
        super.onResume()
        attachObservers()
    }

    override fun setupActionBar(toolbar: Toolbar) {

    }

    override fun attachObservers() {
        with(viewModel) {
            processing.observe(this@DiscoverActivity, Observer<Boolean> { processing -> onProcessChanged(processing) })
            error.observe(this@DiscoverActivity, Observer<String> { error -> onError(error) })
            movies.observe(this@DiscoverActivity, Observer<DiscoverMovies> { discover -> onMoviesResult(discover) })
        }
    }

    override fun detachObservers() {
        with(viewModel) {
            processing.removeObservers(this@DiscoverActivity)
            error.removeObservers(this@DiscoverActivity)
            movies.removeObservers(this@DiscoverActivity)
        }
    }

    override fun bindData() {
        viewModel.movies()
    }

    override fun attachListeners() {

    }

    private fun onProcessChanged(processing: Boolean?) {
        progressBar.visibility = if (processing == true) View.VISIBLE else View.GONE
    }

    private fun onError(message: String?) {
        message?.let {
            AlertDialog.Builder(this)
                    .setTitle(R.string.error)
                    .setMessage(message)
                    .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                    .show()
        }
    }

    private fun onMoviesResult(discover: DiscoverMovies?) {
        moviesAdapter?.setData(discover)
    }

    private fun onMovieResult(movie: Movie?) {
        AlertDialog.Builder(this)
                .setTitle(R.string.info).setMessage(movie?.toString())
                .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                .show()
    }

    private fun setupRecyclerView() {
        moviesAdapter = DiscoverMoviesAdapter { movieId -> onItemClicked(movieId) }
        with(list) {
            layoutManager = LinearLayoutManager(this@DiscoverActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@DiscoverActivity, DividerItemDecoration.VERTICAL))
            adapter = moviesAdapter
        }
    }

    fun onItemClicked(movieId: Int) {

    }

}
