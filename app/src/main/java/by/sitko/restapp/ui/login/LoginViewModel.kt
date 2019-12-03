package by.sitko.restapp.ui.login

import by.android.base.manager.NetworkManager
import by.android.base.manager.ResourceManager
import by.sitko.restapp.R
import by.sitko.restapp.api.ApiInterface
import by.sitko.restapp.api.AuthRequest
import by.sitko.restapp.base.BaseViewModel
import by.sitko.restapp.base.SingleLiveEvent
import by.sitko.restapp.manager.ToastManager
import by.sitko.restapp.util.EMPTY
import by.sitko.restapp.util.isEmailValid
import by.sitko.restapp.util.logError
import by.zmitrocc.shop.manager.PasswordManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
      private val passwordManager: PasswordManager,
      private val toastManager: ToastManager,
      private val networkManager: NetworkManager,
      private val api: ApiInterface,
      private val resourceManager: ResourceManager
) : BaseViewModel() {

    val moveToMainScreen = SingleLiveEvent<Unit>()

    var mail = EMPTY
    var password = EMPTY


    override val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        logError(throwable)
    }

    fun onSignInCLicked() {

        if (networkManager.isNetAvailable().not()) {
            toastManager.showMessage(R.string.no_internet)
            return
        }

        if (mail.isEmailValid().not()) {
            toastManager.showMessage(R.string.email_bad)
            return
        }

        if (password.length < 3) {
            toastManager.showMessage(R.string.password_bad)
            return
        }

        modelScope.launch {
            loadingData.value = true

            val loginRequest = AuthRequest(mail, password)

            try {
                withContext(Dispatchers.IO) {
                    val loginResponse = api.auth(loginRequest).await()

                    val token = loginResponse.token

                    passwordManager.saveToken(token)
                }

                moveToMainScreen.postValue(Unit)

            } catch (e: Exception) {
                toastManager.showMessage(e.message ?: resourceManager.getString(R.string.fail))
            } finally {
                loadingData.postValue(false)
            }
        }
    }
}