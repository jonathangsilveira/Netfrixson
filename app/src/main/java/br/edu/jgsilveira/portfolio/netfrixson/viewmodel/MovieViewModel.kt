package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.util.Log
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.MovieGenres
import br.edu.jgsilveira.portfolio.netfrixson.api.endpoint.MovieEndPoint

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
            try {
                isProcessing = true
                val deferred = movieEndPoint.detailAsync(id)
                Log.d("Coroutine", "After async call")
                val response = deferred.await()
                if (response.isSuccessful) {
                    response.body()?.let { movie ->
                        _movie.value = movie
                    }
                } else
                    _error.value = response.message()
            } catch (e: Exception) {
                val message = "Something went wrong with movie #$id: ${e.message}"
                Log.wtf("MovieViewModel", message, e)
                _error.value = message
            } finally {
                isProcessing = false
            }
            /*val response = withContext(Dispatchers.IO) {
                movieEndPoint.detail(id)
            }
            if (response.isSuccessful) {
                response.body()?.let { movie ->
                    _movie.value = movie
                    launch(Dispatchers.IO) {
                        //TODO Load image
                    }
                    async {  }
                }
            } else
                _error.value = response.message()*/
        }

    }

}