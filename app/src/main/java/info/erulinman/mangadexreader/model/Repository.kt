package info.erulinman.mangadexreader.model

import android.util.Log
import info.erulinman.mangadexreader.api.BASE_URL
import info.erulinman.mangadexreader.api.MangaDexService
import info.erulinman.mangadexreader.model.entities.Manga
import info.erulinman.mangadexreader.model.entities.Relationship
import info.erulinman.mangadexreader.viewmodels.MangaListViewModel
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class Repository {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mangaDexService: MangaDexService = retrofit.create(MangaDexService::class.java)
}