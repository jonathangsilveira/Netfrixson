package br.edu.jgsilveira.portfolio.netfrixson.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.RatedMovies
import br.edu.jgsilveira.portfolio.netfrixson.viewmodel.GuestSessionViewModel

class GuestSessionActivity : AppCompatActivity() {

    private lateinit var list: RecyclerView

    private lateinit var progressBar: ProgressBar

    private lateinit var viewModel: GuestSessionViewModel

    private var ratedMoviesAdapter: RatedMoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_session)
        viewModel = ViewModelProviders.of(this).get(GuestSessionViewModel::class.java)
        list = findViewById(R.id.activity_guest_session_list)
        progressBar = findViewById(R.id.progressBar)
        setupRecyclerView()
        viewModel.createGuestSession()
    }

    override fun onResume() {
        super.onResume()
        attachObservers()
    }

    override fun onPause() {
        super.onPause()
        detachObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.guest_session_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let { option ->
            when (option.itemId) {
                R.id.action_refresh -> {
                    viewModel.ratedMovies()
                    return true
                }
                else -> {
                }
            }
        }
        return false
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

    private fun onResult(ratedMovies: RatedMovies?) {
        ratedMoviesAdapter?.data = ratedMovies
    }

    private fun setupRecyclerView() {
        ratedMoviesAdapter = RatedMoviesAdapter()
        with(list) {
            layoutManager = LinearLayoutManager(this@GuestSessionActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ratedMoviesAdapter
        }
    }

    private fun attachObservers() {
        with(viewModel) {
            processing.observe(this@GuestSessionActivity, Observer<Boolean> { processing -> onProcessChanged(processing) })
            error.observe(this@GuestSessionActivity, Observer<String> { message -> onError(message) })
            result.observe(this@GuestSessionActivity, Observer<RatedMovies> { ratedMovies -> onResult(ratedMovies) })
        }
    }

    private fun detachObservers() {
        with(viewModel) {
            processing.removeObservers(this@GuestSessionActivity)
            error.removeObservers(this@GuestSessionActivity)
            result.removeObservers(this@GuestSessionActivity)
        }
    }

}
