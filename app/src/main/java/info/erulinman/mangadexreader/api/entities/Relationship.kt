package info.erulinman.mangadexreader.api.entities

data class Relationship(
    val id: String,
    val type: String,
) {
    companion object {
        const val AUTHOR = "author"
    }
}
