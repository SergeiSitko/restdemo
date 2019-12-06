package by.sitko.restapp.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import by.sitko.restapp.R
import by.sitko.restapp.ui.login.LoginActivity
import by.sitko.restapp.util.getViewVisibility
import kotlinx.android.synthetic.main.activity_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ProfileActivity::class.java))
        }
    }

    private val viewModel by viewModel<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logOut.setOnClickListener {
            AlertDialog.Builder(this)
                  .setMessage(R.string.are_you_sure_log_out)
                  .setPositiveButton(android.R.string.ok, { _, _ -> viewModel.logout() })
                  .show()
        }

        run.setOnClickListener { viewModel.subscribeToServer() }
        refresh.setOnClickListener { viewModel.refreshData() }
        stop.setOnClickListener { viewModel.unSubscribeToServer() }

        val adapter = TransactionAdapter()
        transactions.adapter = adapter

        viewModel.postsData.observe(this, Observer { transactions ->
            adapter.update(transactions)
        })

        viewModel.loadingData.observe(this, Observer { loading ->
            progress.visibility = getViewVisibility(loading)
            content.visibility = getViewVisibility(loading.not())
        })
        viewModel.moveToLoginScreen.observe(this, Observer {
            LoginActivity.start(this)
            finish()
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.connect()
    }

    override fun onStop() {
        super.onStop()
        viewModel.disConnect()
    }
}
