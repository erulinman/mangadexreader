package info.erulinman.mangadexreader.api

import java.lang.Exception

sealed class NetworkResponse<out T: Any> {
    class Success<out T: Any>(val data: T): NetworkResponse<T>()
    class Failure(val exception: Exception): NetworkResponse<Nothing>()
}