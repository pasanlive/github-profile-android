package com.pasanlive.githubprofile

import android.app.Application
import com.pasanlive.githubprofile.dagger.AppComponent
import com.pasanlive.githubprofile.dagger.AppModule
import com.pasanlive.githubprofile.dagger.DaggerAppComponent
import com.pasanlive.githubprofile.dagger.PresenterModule

class GithubProfileApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: GithubProfileApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .presenterModule(PresenterModule())
            .build()
}