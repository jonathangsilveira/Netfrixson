package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.MovieGenres
import br.edu.jgsilveira.portfolio.netfrixson.api.endpoint.MovieEndPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(application: Application) : AppViewModel(application) {

    private val movieEndPoint: MovieEndPoint by lazy { MovieEndPoint() }

    private val _image: MutableLiveData<Bitmap> by lazy { MutableLiveData<Bitmap>() }

    private val _movie: MutableLiveData<Movie> by lazy { MutableLiveData<Movie>() }

    private val _video: MutableLiveData<Any> by lazy { MutableLiveData<Any>() }

    private val _genres: MutableLiveData<MovieGenres> by lazy { MutableLiveData<MovieGenres>() }

    val image: LiveData<Bitmap>
        get() = _image

    val movie: LiveData<Movie>
        get() = _movie

    val genres: LiveData<MovieGenres>
        get() = _genres

    fun rate(value: Double) {
        //TODO Rate(value)
    }

    fun query(id: Int) {
        launchOnUIScope {
            isProcessing = true
            val response = withContext(Dispatchers.IO) {
                movieEndPoint.detail(id)
            }
            if (response.isSuccessful) {
                response.body()?.let { movie ->
                    _movie.value = movie
                    launch(Dispatchers.IO) {
                        //TODO Load image
                    }
                }
            }else
                _error.value = response.message()
            isProcessing = false
        }
    }

}