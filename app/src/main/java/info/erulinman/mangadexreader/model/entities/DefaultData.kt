package info.erulinman.mangadexreader.model.entities

typealias Manga = DefaultData<MangaAttributes>
typealias Author = DefaultData<AuthorAttributes>

data class DefaultData<T>(
    val id: String,
    val type: String,
    val attributes: T,
    val relationships: List<Relationship>
)