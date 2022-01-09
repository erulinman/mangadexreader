package info.erulinman.mangadexreader.api

import info.erulinman.mangadexreader.api.entities.Author
import info.erulinman.mangadexreader.api.entities.Manga

interface RemoteRepository {

    suspend fun getMangaList(title: String): NetworkResponse<List<Manga>>

    suspend fun getAuthorList(idList: List<String>): NetworkResponse<List<Author>>
}