package info.erulinman.mangadexreader.api

import info.erulinman.mangadexreader.model.entities.Author
import info.erulinman.mangadexreader.model.entities.DefaultResponse
import info.erulinman.mangadexreader.model.entities.Manga
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.mangadex.org"

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