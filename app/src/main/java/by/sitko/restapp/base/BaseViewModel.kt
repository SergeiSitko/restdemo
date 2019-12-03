package by.sitko.restapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseViewModel : ViewModel() {

    protected abstract val exceptionHandler: CoroutineExceptionHandler

    private val viewModelJob = SupervisorJob()

    protected val modelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val loadingData = MutableLiveData(false)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
