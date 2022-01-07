package info.erulinman.mangadexreader.api

import info.erulinman.mangadexreader.api.entities.Author
import info.erulinman.mangadexreader.api.entities.DefaultResponse
import info.erulinman.mangadexreader.api.entities.Manga
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaDexService {
    @GET("/manga")
    suspend fun getMangaListByTitle(
        @Query("title") title: String,
        @Query("limit") limit: Int = 100
    ): Response<DefaultResponse<Manga>>

    @GET("/author")
    suspend fun getAuthorList(
        @Query("ids[]") ids: List<String>,
        @Query("limit") limit: Int = 100
    ) : Response<DefaultResponse<Author>>
}