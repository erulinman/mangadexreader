package info.erulinman.mangadexreader.api.entities

data class TagAttributes(
    val name: Map<String, String>,
    val description: Map<String, String>,
    val group: String,
    val version: Int
)
