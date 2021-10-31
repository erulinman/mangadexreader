package info.erulinman.mangadexreader.model.entities

data class Relationship(
    val id: String,
    val type: String,
) {
    companion object {
        const val AUTHOR = "author"
    }
}
