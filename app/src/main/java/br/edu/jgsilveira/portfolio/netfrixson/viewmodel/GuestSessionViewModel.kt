package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.RatedMovies
import br.edu.jgsilveira.portfolio.retrofitgithubsample.dto.GuestSession

class GuestSessionViewModel(application: Application) : AppViewModel(application) {

    private val _result: MutableLiveData<RatedMovies> by lazy { MutableLiveData<RatedMovies>() }

    private var guestSession: GuestSession? = null

    val result: LiveData<RatedMovies>
        get() = _result

    fun createGuestSession() {
        launchOnUIScope {
            _processing.value = true
            /*kotlin.runCatching {
                GuestSessionEndPoint().newGuestSession()
            }.onSuccess {
                _processing.value = false
                guestSession = it
                ratedMovies()
            }.onFailure {
                _processing.value = false
                _error.value = it
            }*/
        }
    }

    fun ratedMovies() {
        launchOnUIScope {
            /*_processing.value = true
            kotlin.runCatching {
                guestSession?.let { GuestSessionEndPoint().ratedMovies(it) }
            }.onSuccess { ratedMovies ->
                _result.value = ratedMovies
            }.onFailure { exception ->
                _error.value = exception
            }
            _processing.value = false*/
        }
    }

}