package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import br.edu.jgsilveira.portfolio.netfrixson.api.client.DiscoverClient
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class DiscoverViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _processing: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    private val _error: MutableLiveData<Throwable> by lazy { MutableLiveData<Throwable>() }

    private val _movies: MutableLiveData<DiscoverMovies> by lazy {
        MutableLiveData<DiscoverMovies>()
    }

    val processing: LiveData<Boolean>
        get() = _processing

    val error: LiveData<Throwable>
        get() = _error

    val movies: LiveData<DiscoverMovies>
        get() = _movies

    fun movies() {
        uiScope.launch {
            _processing.value = true
            kotlin.runCatching {
                val currentYear = Calendar.getInstance().apply { time = Date() }.get(Calendar.YEAR)
                DiscoverClient().movies(mapOf("api_key" to Api.KEY,
                        "language" to "en-US",
                        "sort_by" to "popularity.desc",
                        "year" to currentYear.toString()))
            }.onSuccess { discoverMovies ->
                _processing.value = false
                _movies.value = discoverMovies
            }.onFailure { e ->
                _processing.value = false
                _error.value = e
            }
        }
    }

}