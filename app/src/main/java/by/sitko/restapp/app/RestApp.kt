package by.sitko.restapp.app

import android.app.Application
import by.sitko.restapp.di.apiModule
import by.sitko.restapp.di.managerModule
import by.sitko.restapp.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RestApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RestApp)
            modules(listOf(viewModels, managerModule, apiModule))
        }
    }
}