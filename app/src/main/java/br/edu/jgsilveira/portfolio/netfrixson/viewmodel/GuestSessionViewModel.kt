package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import br.edu.jgsilveira.portfolio.netfrixson.api.client.GuestSessionClient
import br.edu.jgsilveira.portfolio.netfrixson.api.dto.RatedMovies
import br.edu.jgsilveira.portfolio.retrofitgithubsample.dto.GuestSession
import kotlinx.coroutines.*

class GuestSessionViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _processing: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    private val _error: MutableLiveData<Throwable> by lazy { MutableLiveData<Throwable>() }

    private val _result: MutableLiveData<RatedMovies> by lazy { MutableLiveData<RatedMovies>() }

    private var guestSession: GuestSession? = null

    val processing: LiveData<Boolean>
        get() = _processing

    val error: LiveData<Throwable>
        get() = _error

    val result: LiveData<RatedMovies>
        get() = _result

    fun createGuestSession() {
        uiScope.launch {
            _processing.value = true
            kotlin.runCatching {
                GuestSessionClient().newGuestSession()
            }.onSuccess {
                _processing.value = false
                guestSession = it
                ratedMovies()
            }.onFailure {
                _processing.value = false
                _error.value = it
            }

        }
    }

    fun ratedMovies() {
        uiScope.launch {
            _processing.value = true
            kotlin.runCatching {
                guestSession?.let { GuestSessionClient().ratedMovies(it) }
            }.onSuccess { ratedMovies ->
                _result.value = ratedMovies
            }.onFailure { exception ->
                _error.value = exception
            }
            _processing.value = false
        }
    }

}