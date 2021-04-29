package com.pasanlive.githubprofile.dagger

import com.pasanlive.githubprofile.home.HomeActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        PresenterModule::class,
    ]
)
interface AppComponent {
    fun inject(target: HomeActivity)
}