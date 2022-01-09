package info.erulinman.mangadexreader.api.entities

data class ApiResponse<T>(
    val result: String,
    val response: String,
    val data: List<T>,
    val limit: Int,
    val offset: Int,
    val total: Int
)