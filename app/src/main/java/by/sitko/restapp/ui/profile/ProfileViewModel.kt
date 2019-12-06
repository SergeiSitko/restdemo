package by.sitko.restapp.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import by.android.base.manager.NetworkManager
import by.android.base.manager.ResourceManager
import by.sitko.restapp.R
import by.sitko.restapp.api.ApiInterface
import by.sitko.restapp.api.Info
import by.sitko.restapp.api.LogOutBody
import by.sitko.restapp.api.TransactionResponse
import by.sitko.restapp.base.BaseViewModel
import by.sitko.restapp.base.SingleLiveEvent
import by.sitko.restapp.manager.SocketManager
import by.sitko.restapp.manager.SocketManager.Impl
import by.sitko.restapp.manager.ToastManager
import by.sitko.restapp.util.EMPTY
import by.sitko.restapp.util.logError
import by.zmitrocc.shop.manager.PasswordManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
      private val passwordManager: PasswordManager,
      private val toastManager: ToastManager,
      private val networkManager: NetworkManager,
      private val api: ApiInterface,
      private val resourceManager: ResourceManager
) : BaseViewModel() {


    companion object {
        private val SUBSCRIBE_TO_MESSAGE = "{\"op\":\"unconfirmed_sub\"}"
        private val UNSUBSCRIBE_TO_MESSAGE = "{\"op\":\"unconfirmed_unsub\"}"
        private val PING = "{\"op\":\"ping\"}"
    }

    val moveToLoginScreen = SingleLiveEvent<Unit>()
    val postsData = MutableLiveData(mutableListOf<TransactionResponse>())

    private val posts = mutableListOf<TransactionResponse>()
    private val socketListener = object : SocketManager.SocketListener {
        override fun onMessage(message: String) {
            val element = TransactionResponse.newInstance(message)

            if (element.x != null) {
                posts.add(element)
                postsData.postValue(posts)
            }
        }

        override fun onFail(e: Exception) {
            logError(e)
        }
    }
    override val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        logError(throwable)
    }
    private val socketManager = Impl(socketListener) as SocketManager
    private var profileInfo: Info? = null

    init {
        loadProfile()

        initSocket()

        socketManager.init()
    }

    fun connect() {
        modelScope.launch(Dispatchers.IO) { socketManager.connect() }
    }

    fun disConnect() {
        modelScope.launch(Dispatchers.IO) { socketManager.disconnect() }
    }

    fun subscribeToServer() {
        modelScope.launch(Dispatchers.IO) { socketManager.sendToServer(SUBSCRIBE_TO_MESSAGE) }
    }

    fun unSubscribeToServer() {
        modelScope.launch(Dispatchers.IO) { socketManager.sendToServer(UNSUBSCRIBE_TO_MESSAGE) }
    }

    fun refreshData() {
        posts.clear()
        postsData.postValue(posts)
    }

    private fun loadProfile() {
        if (networkManager.isNetAvailable().not()) {
            toastManager.showMessage(R.string.no_internet)
            return
        }

        viewModelScope.launch {
            loadingData.value = true

            try {
                withContext(Dispatchers.IO) {
                    val profileResponse = api.getProfile().await()

                    val info = profileResponse.info

                    profileInfo = info
                    passwordManager.saveSession(info.session ?: "no session")
                }

                moveToLoginScreen.postValue(Unit)

            } catch (e: Exception) {
                toastManager.showMessage(e.message ?: resourceManager.getString(R.string.fail))
            } finally {
                loadingData.postValue(false)
            }
        }
    }

    fun logout() {
        if (networkManager.isNetAvailable().not()) {
            toastManager.showMessage(R.string.no_internet)
            return
        }

        if (profileInfo == null) {
            toastManager.showMessage(R.string.no_prifile_data)
            return
        }

        viewModelScope.launch {
            loadingData.value = true

            try {
                withContext(Dispatchers.IO) {
                    api.logOut(LogOutBody(passwordManager.getSession())).await()

                    passwordManager.saveToken(EMPTY)

                    passwordManager.saveSession(EMPTY)
                }

                moveToLoginScreen.postValue(Unit)

            } catch (e: Exception) {
                toastManager.showMessage(e.message ?: resourceManager.getString(R.string.fail))
            } finally {
                loadingData.postValue(false)
            }
        }
    }

    private fun initSocket() {
        modelScope.launch(Dispatchers.IO) {
            socketManager.init()
            socketManager.sendToServer(PING)
        }
    }
}
