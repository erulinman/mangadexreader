package info.erulinman.mangadexreader.api.entities

typealias Manga = ApiData<MangaAttributes>
typealias Author = ApiData<AuthorAttributes>

data class ApiData<T>(
    val id: String,
    val type: String,
    val attributes: T,
    val relationships: List<Relationship>
)