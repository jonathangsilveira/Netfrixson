package br.edu.jgsilveira.portfolio.netfrixson.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies
import br.edu.jgsilveira.portfolio.netfrixson.viewmodel.DiscoverViewModel
import kotlinx.android.synthetic.main.activity_discover.*

class DiscoverActivity : AppCompatActivity(), IActivityFragment {

    private lateinit var viewModel: DiscoverViewModel

    private var moviesAdapter: DiscoverMoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover)
        viewModel = ViewModelProviders.of(this).get(DiscoverViewModel::class.java)
        supportActionBar?.setTitle(R.string.discover)
        setupRecyclerView()
        bindData()
    }

    override fun onResume() {
        super.onResume()
        attachObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        detachObservers()
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

    private fun setupRecyclerView() {
        moviesAdapter = DiscoverMoviesAdapter { movieId -> onItemClicked(movieId) }
        with(activity_discover_list) {
            layoutManager = LinearLayoutManager(this@DiscoverActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@DiscoverActivity, DividerItemDecoration.VERTICAL))
            adapter = moviesAdapter
        }
    }

    private fun onItemClicked(movieId: Int) {
        startActivity(Intent(this, MovieActivity::class.java).apply {
            putExtra("movieId", movieId)
        })
    }

}
