package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    protected val _processing: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    protected val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val processing: LiveData<Boolean>
        get() = _processing

    val error: LiveData<String>
        get() = _error

    protected fun launchOnUIScope(block: suspend CoroutineScope.() -> Unit) {
        uiScope.launch { block() }
    }

}