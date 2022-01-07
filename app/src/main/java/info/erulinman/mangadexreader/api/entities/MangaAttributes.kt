package info.erulinman.mangadexreader.api.entities

data class MangaAttributes(
    val title: Map<String, String>,
    val altTitles: List<Map<String, String>>,
    val description: Map<String, String>,
    val isLocked: Boolean,
    val links: Map<String, String>,
    val originalLanguage: String,
    val lastVolume: String?,
    val lastChapter: String?,
    val publicationDemographic: String?,
    val status: String?,
    val year: Int?,
    val contentRating: String,
    val tags: List<Tag>,
    val state: String,
    val version: Int,
    val createdAt: String,
    val updatedAt: String
)