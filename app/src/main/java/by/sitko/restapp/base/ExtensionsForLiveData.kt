package by.sitko.restapp.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import by.sitko.restapp.util.EMPTY
import java.util.concurrent.atomic.AtomicBoolean

/**
 * LiveData with init state and methods to set and get
 *
 * @param data Init state
 * @param updateFunction Callback to update data while data is switching in active mode
 */
class FilledData<T>(
    data: T,
    private val updateFunction: (() -> T)? = null

) : MutableLiveData<T>() {
    init {
        value = data
    }

    fun getData(): T = value!!

    fun setData(newData: T) {
        value = newData
    }

    fun postData(newData: T) {
        postValue(newData)
    }

    fun refresh() {
        val localData = value
        value = localData
    }

    override fun onActive() {
        super.onActive()
        updateFunction?.let { setData(it.invoke()) }
    }
}

/**
 * LiveData for EditText and to prevent reSubscribe while typing.
 *
 * @param initValue Init state
 */
class EditTextData(
    val initValue: String = EMPTY
) : MutableLiveData<String>() {

    private var changedData = initValue

    init {
        value = initValue
    }

    /**
     * This method should use only subscribe time
     * @Deprecated This method should be deleted if it will be redundant.
     */
    private fun getData(): String = value!!

    // This method should use in other manipulations.
    fun getContent() = changedData

    fun setContentData(newContent: String) {
        changedData = newContent
    }

    fun setData(newContent: String) {
        changedData = newContent
        value = changedData
    }

    override fun onActive() {
        super.onActive()
        value = changedData
    }
}

//for single event data. Start activity, show dialog, start fragment.
class SingleLiveEvent<T> : MutableLiveData<T>() {

    companion object {
        private val TAG = "SingleLiveEvent"
    }

    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }

        super.observe(
            owner,
            object : Observer<T> {
                override fun onChanged(t: T) {
                    if (mPending.compareAndSet(true, false)) {
                        observer.onChanged(t);
                    }
                }
            }
        )
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        setValue(null)
    }
}
