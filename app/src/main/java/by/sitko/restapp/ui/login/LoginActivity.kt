package by.sitko.restapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import by.sitko.restapp.R
import by.sitko.restapp.ui.profile.ProfileActivity
import by.sitko.restapp.util.doAfterTextChanged
import by.sitko.restapp.util.getViewVisibility
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailInput.doAfterTextChanged { mail -> viewModel.mail = mail }
        passwordInput.doAfterTextChanged { password -> viewModel.password = password }

        login.setOnClickListener { viewModel.onSignInCLicked() }

        viewModel.loadingData.observe(this, Observer { shouldShowProgress ->
            progress.visibility = getViewVisibility(shouldShowProgress)
            content.visibility = getViewVisibility(shouldShowProgress.not())
        })

        viewModel.moveToProfileScreen.observe(this, Observer {
            ProfileActivity.start(this)
            finish()
        })
    }
}
