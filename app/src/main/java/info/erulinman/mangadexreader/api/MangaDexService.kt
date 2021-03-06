package info.erulinman.mangadexreader.api

import info.erulinman.mangadexreader.api.entities.Author
import info.erulinman.mangadexreader.api.entities.ApiResponse
import info.erulinman.mangadexreader.api.entities.Manga
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaDexService {

    @GET("/manga")
    suspend fun getMangaList(
        @Query("title") title: String,
        @Query("limit") limit: Int = 100 // 100 - max for api or error 400
    ): ApiResponse<Manga>

    @GET("/author")
    suspend fun getAuthorList(
        @Query("ids[]") ids: List<String>,
        @Query("limit") limit: Int = 100 // 100 - max for api or error 400
    ) : ApiResponse<Author>
}