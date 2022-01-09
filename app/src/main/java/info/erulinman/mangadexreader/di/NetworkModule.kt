package info.erulinman.mangadexreader.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import info.erulinman.mangadexreader.api.MangaDexService
import info.erulinman.mangadexreader.api.RemoteRepository
import info.erulinman.mangadexreader.api.RemoteRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    private companion object {
        const val BASE_URL = "https://api.mangadex.org"
    }

    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(MangaDexService::class.java)
}