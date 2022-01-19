package info.erulinman.mangadexreader.api

import info.erulinman.mangadexreader.api.entities.Manga

interface MangaRepository {

    suspend fun getMangaList(title: String): NetworkResponse<List<Manga>>
}