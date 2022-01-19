package info.erulinman.mangadexreader.api

import java.lang.Exception
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val service: MangaDexService
) : MangaRepository {

    override suspend fun getMangaList(title: String) = try {
        val apiResponse = service.getMangaList(title)
        NetworkResponse.Success(apiResponse.data)
    } catch (e: Exception) {
        NetworkResponse.Failure(e)
    }
}