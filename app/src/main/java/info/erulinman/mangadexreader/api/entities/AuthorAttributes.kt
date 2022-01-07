package info.erulinman.mangadexreader.api.entities

data class AuthorAttributes(
    val name: String,
    val imageUrl: String,
    val biography: Map<String, String>,
    val twitter: String?,
    val pixiv: String?,
    val melonBook: String?,
    val fanBox: String?,
    val booth: String?,
    val nicoVideo: String?,
    val skeb: String?,
    val fantia: String?,
    val tumblr: String?,
    val youtube: String?,
    val website: String?,
    val version: Int,
    val createdAt: String,
    val updatedAt: String
)