package info.erulinman.mangadexreader.model.entities

data class DefaultResponse<T>(
    val result: String,
    val response: String,
    val data: List<T>,
    val limit: Int,
    val offset: Int,
    val total: Int
)