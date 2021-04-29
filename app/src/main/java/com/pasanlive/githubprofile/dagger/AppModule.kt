package com.pasanlive.githubprofile.dagger

import android.app.Application
import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.pasanlive.githubprofile.BuildConfig
import com.pasanlive.githubprofile.common.Constants
import com.pasanlive.githubprofile.home.repository.GithubDataRepository
import com.pasanlive.githubprofile.home.repository.GithubGraphQLDataRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "bearer ${BuildConfig.GITHUB_API_TOKEN}")
                .build()

            chain.proceed(request)
        }
        .build()

    @Provides
    @Singleton
    fun provideSqlNormalizedCacheFactory(applicationContext: Context): SqlNormalizedCacheFactory =
        SqlNormalizedCacheFactory(applicationContext, Constants.cacheDBName)

    @Provides
    @Singleton
    fun provideApolloClient(
        okHttpClient: OkHttpClient,
        sqlNormalizedCacheFactory: SqlNormalizedCacheFactory
    ): ApolloClient = ApolloClient.builder().okHttpClient(okHttpClient)
        .serverUrl(Constants.githubGraphQLUrl)
        .normalizedCache(sqlNormalizedCacheFactory)
        .build()

    @Provides
    @Singleton
    fun provideGithubDateRepository(
        apolloClient: ApolloClient
    ): GithubDataRepository = GithubGraphQLDataRepository(apolloClient)
}