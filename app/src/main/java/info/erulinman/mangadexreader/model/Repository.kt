package info.erulinman.mangadexreader.model

import info.erulinman.mangadexreader.api.BASE_URL
import info.erulinman.mangadexreader.api.MangaDexService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mangaDexService: MangaDexService = retrofit.create(MangaDexService::class.java)
}