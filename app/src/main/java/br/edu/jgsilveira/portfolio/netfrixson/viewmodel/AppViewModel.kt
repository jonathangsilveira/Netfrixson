package br.edu.jgsilveira.portfolio.netfrixson.viewmodel

import android.app.ActivityManager
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val isOnline: Boolean
        get() {
            val connectivityManager: ConnectivityManager? =
                    getApplication<Application>()
                            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager?.activeNetworkInfo?.isConnected ?: false
        }

    private val memoryInfo: ActivityManager.MemoryInfo
        get() {
            val activityManager = getApplication<Application>().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            return ActivityManager.MemoryInfo().also { memoryInfo ->
                activityManager.getMemoryInfo(memoryInfo)
            }
        }

    protected val _processing: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    protected val _error: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    protected var isProcessing: Boolean
        get() = _processing.value ?: false
        set(value) {
            _processing.value = value
        }

    val processing: LiveData<Boolean>
        get() = _processing

    val error: LiveData<String>
        get() = _error

    val lowMemory: Boolean
        get() = memoryInfo.lowMemory

    protected fun launchOnUIScope(block: suspend CoroutineScope.() -> Unit) {
        uiScope.launch { block() }
    }

    protected fun printStatInfos() {
        with(memoryInfo) {
            println("Avaliable memory: $availMem")
            println("Total memory: $totalMem")
            println("Low memory: $lowMemory")
        }
    }

}