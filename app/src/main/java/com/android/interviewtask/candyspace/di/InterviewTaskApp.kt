package com.android.interviewtask.candyspace.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InterviewTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@InterviewTaskApp)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}