package info.erulinman.mangadexreader.api.entities

data class Tag(
    val id: String,
    val type: String,
    val attributes: TagAttributes,
    val relationship: List<Relationship>
)