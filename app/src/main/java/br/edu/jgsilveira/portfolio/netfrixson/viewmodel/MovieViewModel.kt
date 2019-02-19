package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import br.edu.jgsilveira.portfolio.netfrixson.R
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.MovieGenres
import br.edu.jgsilveira.portfolio.netfrixson.api.endpoint.MovieEndPoint
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieViewModel(application: Application) : AppViewModel(application) {

    private val endPoint: MovieEndPoint by lazy { MovieEndPoint() }

    private val _backdropImage: MutableLiveData<Drawable> by lazy { MutableLiveData<Drawable>() }

    private val _movie: MutableLiveData<Movie> by lazy { MutableLiveData<Movie>() }

    private val _video: MutableLiveData<Any> by lazy { MutableLiveData<Any>() }

    private val _genres: MutableLiveData<MovieGenres> by lazy { MutableLiveData<MovieGenres>() }

    val backdropImage: LiveData<Drawable>
        get() = _backdropImage

    val movie: LiveData<Movie>
        get() = _movie

    val genres: LiveData<MovieGenres>
        get() = _genres

    private fun loadBackdrop() {
        _movie.value?.let { movie ->
            Picasso.get()
                    .load(movie.backdropPath)
                    .placeholder(R.drawable.abc_ab_share_pack_mtrl_alpha)
                    .error(R.drawable.abc_ab_share_pack_mtrl_alpha)
                    .centerCrop()
                    .into(BackdropTarget(_backdropImage, getApplication<Application>().resources))
        }
    }

    fun rate(value: Double) {
        //TODO Rate(value)
    }

    fun query(id: Int) {
        launchOnUIScope {
            try {
                isProcessing = true
                val response = withContext(Dispatchers.IO) {
                    endPoint.detail(id)
                }
                if (response.isSuccessful) {
                    response.body()?.let { movie ->
                        _movie.value = movie
                        loadBackdrop()
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
        }

    }

    inner class BackdropTarget(
            val liveData: MutableLiveData<Drawable>,
            val resources: Resources
    ) : Target {

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            liveData.value = placeHolderDrawable
        }

        override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
            liveData.value = errorDrawable
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            liveData.value = BitmapDrawable(resources, bitmap)
        }

    }

}