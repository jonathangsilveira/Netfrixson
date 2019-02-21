package br.edu.jgsilveira.portfolio.netfrixson.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.pagueveloz.tefandroid.utils.format
import br.com.pagueveloz.tefandroid.utils.formatDate
import br.com.pagueveloz.tefandroid.utils.toCurrency
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.content_movie.*
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt(ID, 0)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            val activity = it as AppCompatActivity
            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.title = ""
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        viewModel.error.observe(this, errorObserver())
        viewModel.movie.observe(this, detailsObserver())
        viewModel.backdropImage.observe(this, backdropImageObserver())
        id?.let {
            viewModel.query(it)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(context as FragmentActivity).get(MovieViewModel::class.java)
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun errorObserver() = Observer<String> { error ->
        activity?.let { activity ->
            AlertDialog.Builder(activity).apply {
                setCancelable(false)
                setTitle(R.string.error)
                setMessage(error)
                setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
            }.show()
        }
    }

    private fun detailsObserver() = Observer<Movie> { movie ->
        movie?.let { update(movie) }
    }

    private fun update(movie: Movie) {
        activity?.let {
            val activity = it as AppCompatActivity
            activity.supportActionBar?.title = movie.originalTitle
        }
        moviePopularity.text = movie.popularity.format()
        movieBudget.text = movie.budget.toCurrency()
        movieReleaseDate.text = movie.releaseDate.formatDate()
        movieRuntime.text = movie.runtime.toString()
        movieVoteAverage.text = movie.voteAverage.format()
        movieOverview.text = movie.overview
    }

    private fun backdropImageObserver() = Observer<Drawable> { image ->
        backdropImage.setImageDrawable(image)
    }

    companion object {

        private const val ID = "id"

        const val TAG = "MovieFragment"

        fun newInstance(id: Int?): MovieFragment = MovieFragment().apply {
            arguments = Bundle().apply {
                id?.let { putInt(ID, id) }
            }
        }

    }

}
