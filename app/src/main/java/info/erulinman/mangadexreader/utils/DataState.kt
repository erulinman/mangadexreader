package info.erulinman.mangadexreader.utils

sealed class DataState<out T> {
    object Loading: DataState<Nothing>()
    object Empty: DataState<Nothing>()
    data class Loaded<out T>(val data: T): DataState<T>()
    data class Error(val msg: String): DataState<Nothing>()
}