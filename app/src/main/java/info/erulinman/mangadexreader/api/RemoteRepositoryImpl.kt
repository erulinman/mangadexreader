package info.erulinman.mangadexreader.api

import java.lang.Exception
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val service: MangaDexService
) : RemoteRepository {

    override suspend fun getMangaList(title: String) = try {
        val apiResponse = service.getMangaList(title)
        NetworkResponse.Success(apiResponse.data)
    } catch (e: Exception) {
        NetworkResponse.Failure(e)
    }

    override suspend fun getAuthorList(idList: List<String>) = try {
        val apiResponse = service.getAuthorList(idList)
        NetworkResponse.Success(apiResponse.data)
    } catch (e: Exception) {
        NetworkResponse.Failure(e)
    }
}