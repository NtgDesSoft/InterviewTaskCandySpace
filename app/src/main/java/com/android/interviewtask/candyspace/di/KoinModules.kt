package com.android.interviewtask.candyspace.di

import com.android.interviewtask.candyspace.rest.StackExchangeApi
import com.android.interviewtask.candyspace.rest.StackExchangeRepository
import com.android.interviewtask.candyspace.rest.StackExchangeRepositoryImpl
import com.android.interviewtask.candyspace.ui.userslist.UserViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {

    // provides the weather repository implementation
    fun provideStackExchangeRepo(stackExchangeApi: StackExchangeApi): StackExchangeRepository = StackExchangeRepositoryImpl(stackExchangeApi)

    // provide Gson object
    fun provideGson() = GsonBuilder().create()

    // provide logging interceptor
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    // provide okhttp client
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    // providing the retrofit builder
    fun provideStackExchangeApi(okHttpClient: OkHttpClient, gson: Gson): StackExchangeApi =
        Retrofit.Builder()
            .baseUrl(StackExchangeApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(StackExchangeApi::class.java)

    single { provideGson() }
    single { provideLoggingInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideStackExchangeApi(get(), get()) }
    single { provideStackExchangeRepo(get()) }
}

val viewModelModule = module {
    viewModel {
        UserViewModel(get())
    }
}