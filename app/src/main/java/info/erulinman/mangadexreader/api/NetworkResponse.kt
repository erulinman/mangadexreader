package info.erulinman.mangadexreader.api

import java.lang.Exception

sealed class NetworkResponse<out T> {
    class Success<out T>(val data: T): NetworkResponse<T>()
    class Failure(val exception: Exception): NetworkResponse<Nothing>()
}