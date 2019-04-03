package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.Movie
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.MovieGenres
import br.edu.jgsilveira.portfolio.netfrixson.api.endpoint.MovieEndPoint
import br.edu.jgsilveira.portfolio.netfrixson.common.Result
import br.edu.jgsilveira.portfolio.netfrixson.common.RuntimeSettings.configuration
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
        try {
            _movie.value?.let { movie ->
                configuration?.apply {
                    Picasso.get()
                            .load("${images.secureBaseUrl}w500${movie.backdropPath}")
                            //.placeholder(R.drawable.abc_ab_share_pack_mtrl_alpha)
                            //.error(R.drawable.abc_ab_share_pack_mtrl_alpha)
                            .into(BackdropTarget(_backdropImage, getApplication<Application>().resources))
                }
            }
            kotlin.runCatching {  }
        } catch (e: Exception) {
            Log.wtf(TAG, "Could not load backdrop image D:", e)
        }
    }

    fun rate(value: Double) {
        //TODO Rate(value)
    }

    fun query(id: Int) {
        launchOnUIScope {
            Log.d("query", "Before call detail method")
            detail(id)
            val deferred = async(Dispatchers.IO) {
                Log.d("async", "Before async call detail")
                endPoint.detail(id)
            }
            Log.d("query", "After call detail method")
            deferred.await()
            Log.d("query", "After async call detail")
            /*try {
                isProcessing = true
                withContext(Dispatchers.IO) {
                    endPoint.detail(id)
                }.onSuccess { dto ->
                    _movie.value = dto
                    loadBackdrop()
                }.onFailure { _, message, _ ->
                    _error.value = message
                }
                withContext(Dispatchers.IO) {
                    endPoint.genres()
                }.onSuccess { movieGenres ->
                    _genres.value = movieGenres
                }
            } catch (e: Exception) {
                val message = "Something went wrong with movie #$id: ${e.message}"
                Log.wtf("MovieViewModel", message, e)
                _error.value = message
            } finally {
                isProcessing = false
            }*/
        }
    }

    private suspend fun detail(id: Int) = suspendCoroutine<Result<Movie>> { continuation ->
        Log.d("detail", "Before call endpoint method")
        continuation.resume(endPoint.detail(id))
        Log.d("detail", "After call endpoint method")
    }

    inner class BackdropTarget(
            private val liveData: MutableLiveData<Drawable>,
            private val resources: Resources
    ) : Target {

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            liveData.value = placeHolderDrawable
        }

        override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
            liveData.value = errorDrawable
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            Log.d(TAG, "Bitmap dimens: width ${bitmap?.width}/ height ${bitmap?.height}")
            liveData.value = BitmapDrawable(resources, bitmap)
        }

    }

    companion object {

        private const val TAG = "MovieViewModel"

    }

}