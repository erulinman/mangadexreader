package info.erulinman.mangadexreader.di

import dagger.Module
import dagger.Provides
import info.erulinman.mangadexreader.api.MangaDexService
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
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MangaDexService =
        retrofit.create(MangaDexService::class.java)
}