package info.erulinman.mangadexreader.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.mangadex.org"

class RemoteRepositoryImpl : RemoteRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mangaDexService: MangaDexService = retrofit.create(MangaDexService::class.java)
}