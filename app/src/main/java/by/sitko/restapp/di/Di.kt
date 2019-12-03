package by.sitko.restapp.di

import android.os.Handler
import android.os.Looper
import by.android.base.manager.ImplNetworkManager
import by.android.base.manager.NetworkManager
import by.android.base.manager.ResourceManager
import by.android.base.manager.ResourceManager.ImpResourceManager
import by.sitko.restapp.api.ApiInterface
import by.sitko.restapp.manager.ToastManager
import by.sitko.restapp.ui.login.LoginViewModel
import by.zmitrocc.shop.manager.PasswordManager
import by.zmitrocc.shop.manager.PasswordManager.Impl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val managerModule = module {
    single { Handler(Looper.getMainLooper()) }

    single<ToastManager> { ToastManager.Impl(get(), get()) }
    factory<PasswordManager> { Impl(get()) }
    single<NetworkManager> { ImplNetworkManager(get()) }
    single<ResourceManager> { ImpResourceManager(get()) }
}

val viewModels = module {
    viewModel { LoginViewModel(get(), get(), get(), get(), get()) }
}

val apiModule = module {
    single {

        val passwordManager = get<PasswordManager>()

        val tokenInterceptor = Interceptor { chain ->
            val request =
                chain
                    .request()
                    .newBuilder()
                    .addHeader("X-Access-Token", passwordManager.getToken())
                    .build()
            chain.proceed(request)
        }

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient =
            OkHttpClient.Builder()
                .callTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(tokenInterceptor)
                .addInterceptor(interceptor)
                .build()

        val retrofit =
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.dev.karta.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

        retrofit.create(ApiInterface::class.java)
    }
}
