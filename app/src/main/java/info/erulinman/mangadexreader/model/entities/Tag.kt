package info.erulinman.mangadexreader.model.entities

data class Tag(
    val id: String,
    val type: String,
    val attributes: TagAttributes,
    val relationship: List<Relationship>
)