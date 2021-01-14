package android.developer.project.di

import android.content.Context
import android.developer.project.BuildConfig
import android.developer.project.data.remote.Api
import android.developer.project.network.MissingInternetConnectionInterceptor
import android.developer.project.ui.utils.LocalDateTimeConverter
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicOkHttpClient

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkService(@NetworkService retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides @NetworkService
    fun provideRetrofit(@BasicOkHttpClient okHttpClient: OkHttpClient, gson: Gson): Retrofit{
        return createRetrofit(okHttpClient, gson)
    }

    @Singleton
    @Provides @BasicOkHttpClient
    fun provideHttpClient(missingInternetConnectionInterceptor: MissingInternetConnectionInterceptor): OkHttpClient {
        return createOkHttpClient(missingInternetConnectionInterceptor)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson{
        return GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter()).create()
    }

    @Singleton
    @Provides
    fun provideMissingConnectionInterceptor(@ApplicationContext context: Context): MissingInternetConnectionInterceptor {
        return MissingInternetConnectionInterceptor(context)
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_DEVELOPER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun createOkHttpClient(
        missingInternetConnectionInterceptor: MissingInternetConnectionInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if(BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }

        builder.addNetworkInterceptor(missingInternetConnectionInterceptor)

        return builder.build()
    }
}