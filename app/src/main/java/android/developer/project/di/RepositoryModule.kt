package android.developer.project.di

import android.developer.project.data.local.PreferenceHelper
import android.developer.project.data.remote.Api
import android.developer.project.data.repository.GithubRepository
import android.developer.project.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(api: Api, preferenceHelper: PreferenceHelper): UserRepository {
        return UserRepository(api, preferenceHelper)
    }

    @Singleton
    @Provides
    fun provideGithubRepository(api: Api, preferenceHelper: PreferenceHelper): GithubRepository {
        return GithubRepository(api, preferenceHelper)
    }
}