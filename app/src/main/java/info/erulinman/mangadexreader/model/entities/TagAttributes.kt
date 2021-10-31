package info.erulinman.mangadexreader.model.entities

data class TagAttributes(
    val name: Map<String, String>,
    val description: Map<String, String>,
    val group: String,
    val version: Int
)
