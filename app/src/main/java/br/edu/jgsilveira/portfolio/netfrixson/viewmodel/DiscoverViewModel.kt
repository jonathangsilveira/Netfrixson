package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import br.edu.jgsilveira.portfolio.netfrixson.api.Api
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.DiscoverMovies
import br.edu.jgsilveira.portfolio.netfrixson.api.endpoint.DiscoverEndPoint
import br.edu.jgsilveira.portfolio.netfrixson.api.endpoint.GuestSessionEndPoint
import br.edu.jgsilveira.portfolio.netfrixson.common.RuntimeSettings.configuration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class DiscoverViewModel(application: Application) : AppViewModel(application) {

    private val endpoint: DiscoverEndPoint by lazy { DiscoverEndPoint() }

    private val sessionEndPoint: GuestSessionEndPoint by lazy { GuestSessionEndPoint() }

    private val _movies: MutableLiveData<DiscoverMovies> by lazy {
        MutableLiveData<DiscoverMovies>()
    }

    val movies: LiveData<DiscoverMovies>
        get() = _movies

    fun movies() {
        launchOnUIScope {
            try {
                _processing.value = true
                launch(Dispatchers.IO) {
                    try {
                        sessionEndPoint.configuration().let { response ->
                            if (response.isSuccessful)
                                configuration = response.body()
                        }
                    } catch (e: Exception) {
                        Log.wtf(TAG, "Something went wrong with configuration", e)
                    }
                }
                val currentYear = Calendar.getInstance().apply { time = Date() }.get(Calendar.YEAR)
                val response = withContext(Dispatchers.IO) {
                    endpoint.movies(mapOf("api_key" to Api.KEY,
                            "language" to "en-US",
                            "sort_by" to "popularity.desc",
                            "year" to currentYear.toString()))
                }
                if (response.isSuccessful)
                    _movies.value = response.body()
                else
                    _error.value = response.message()
            } catch (e: Exception) {
                Log.wtf(TAG, "Something went wrong with movies discover", e)
                _error.value = e.message
            } finally {
                _processing.value = false
            }
        }
    }

    fun checkConfiguration() {
        launchOnUIScope {
            try {
                _processing.value = true
                val response = withContext(Dispatchers.IO) {
                    sessionEndPoint.configuration()
                }
                if (response.isSuccessful)
                    configuration = response.body()
                else
                    _error.value = response.message()
            } catch (e: Exception) {
                Log.wtf(TAG, "Something went wrong with movies discover", e)
                _error.value = e.message
            } finally {
                _processing.value = false
            }
        }
    }

    companion object {

        private const val TAG = "DiscoverViewModel"

    }

}