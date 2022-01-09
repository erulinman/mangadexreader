package info.erulinman.mangadexreader.api

import java.lang.Exception
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val service: MangaDexService
) : RemoteRepository {

class RemoteRepositoryImpl : RemoteRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mangaDexService: MangaDexService = retrofit.create(MangaDexService::class.java)
}