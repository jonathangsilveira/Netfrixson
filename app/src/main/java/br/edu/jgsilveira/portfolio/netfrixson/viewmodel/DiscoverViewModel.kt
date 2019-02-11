package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies
import br.edu.jgsilveira.portfolio.netfrixson.api.endpoint.DiscoverEndPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class DiscoverViewModel(application: Application) : AppViewModel(application) {

    private val endpoint: DiscoverEndPoint by lazy { DiscoverEndPoint() }

    private val _movies: MutableLiveData<DiscoverMovies> by lazy {
        MutableLiveData<DiscoverMovies>()
    }

    val movies: LiveData<DiscoverMovies>
        get() = _movies

    fun movies() {
        launchOnUIScope {
            try {
                _processing.value = true
                val currentYear = Calendar.getInstance().apply { time = Date() }.get(Calendar.YEAR)
                val response = withContext(Dispatchers.IO) {
                    endpoint.movies(mapOf(/*"api_key" to Api.KEY,*/
                            "language" to "en-US",
                            "sort_by" to "popularity.desc",
                            "year" to currentYear.toString()))
                }
                if (response.isSuccessful)
                    _movies.value = response.body()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _processing.value = true
            }
        }
    }

}