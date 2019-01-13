package br.edu.jgsilveira.portfolio.netfrixson.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.viewmodel.DiscoverViewModel

class DiscoverActivity : AppCompatActivity(), IActivity, DiscoverMoviesAdapter.OnItemClickListener {

    private lateinit var list: RecyclerView

    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: DiscoverViewModel

    private var moviesAdapter: DiscoverMoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)
        viewModel = ViewModelProviders.of(this).get(DiscoverViewModel::class.java)
        initReferences()
        bindData()
    }

    override fun onResume() {
        super.onResume()
        attachObservers()
    }

    override fun onPause() {
        super.onPause()
        detachObservers()
    }

    private fun onProcessChanged(processing: Boolean?) {
        progressBar.visibility = if (processing == true) View.VISIBLE else View.GONE
    }

    private fun onError(e: Throwable?) {
        e?.let {
            AlertDialog.Builder(this)
                    .setTitle(R.string.error)
                    .setMessage(e.message)
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
        moviesAdapter = DiscoverMoviesAdapter().apply { setOnItemClickListener(this@DiscoverActivity) }
        with(list) {
            layoutManager = LinearLayoutManager(this@DiscoverActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@DiscoverActivity, DividerItemDecoration.VERTICAL))
            adapter = moviesAdapter
        }
    }

    override fun attachObservers() {
        with(viewModel) {
            processing.observe(this@DiscoverActivity, Observer<Boolean> { processing -> onProcessChanged(processing) })
            error.observe(this@DiscoverActivity, Observer<Throwable> { error -> onError(error) })
            movies.observe(this@DiscoverActivity, Observer<DiscoverMovies> { discover -> onMoviesResult(discover) })
            movie.observe(this@DiscoverActivity, Observer<Movie> { movie -> onMovieResult(movie) })
        }
    }

    override fun detachObservers() {
        with(viewModel) {
            processing.removeObservers(this@DiscoverActivity)
            error.removeObservers(this@DiscoverActivity)
            movies.removeObservers(this@DiscoverActivity)
        }
    }

    override fun setupActionBar() {

    }

    override fun initReferences() {
        list = findViewById(R.id.activity_discover_list)
        progressBar = findViewById(R.id.progressBar)
        setupRecyclerView()
    }

    override fun bindData() {
        viewModel.movies()
    }

    override fun attachListeners() {

    }

    override fun onItemClicked(movieId: Int) {
        viewModel.movie(movieId)
    }

}
