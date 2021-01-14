package android.developer.project.di

import android.content.Context
import android.developer.project.data.local.AppPreferenceHelper
import android.developer.project.data.local.PreferenceHelper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object StorageModule {

    @Singleton
    @Provides
    fun providePreferenceHelper(@ApplicationContext context: Context, gson: Gson): PreferenceHelper {
        return AppPreferenceHelper(context, gson)
    }
}