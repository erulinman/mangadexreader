package info.erulinman.mangadexreader.mangalist

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import info.erulinman.mangadexreader.api.RemoteRepositoryImpl
import info.erulinman.mangadexreader.api.entities.Author
import info.erulinman.mangadexreader.api.entities.Manga
import info.erulinman.mangadexreader.api.entities.Relationship
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.lang.IllegalStateException

class MangaListViewModel(private val repository: RemoteRepositoryImpl) : ViewModel() {

    private val _mangaList = MutableLiveData<List<Manga>>()
    val mangaList: LiveData<List<Manga>>
        get() = _mangaList

    private var _authorsIds = mutableListOf<String>()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _authors = MutableLiveData<List<Author>>()
    val authors: LiveData<List<Author>>
        get() = _authors

    fun loadData(title: String) = viewModelScope.launch(Dispatchers.IO) {
        _authorsIds.clear()
        _loading.postValue(true)
        loadMangaList(title)
        loadAuthorList()
        _loading.postValue(false)
    }

    private suspend fun loadMangaList(title: String) {
        val mangaListResponse = try {
            repository.mangaDexService.getMangaListByTitle(title)
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
            return
        } catch (e: IOException) {
            Log.e(TAG, "IOException, you might not have internet connection ")
            return
        }
        if (mangaListResponse.isSuccessful) {
            Log.i(TAG, "mangaListResponse is successful")
            mangaListResponse.body()?.let { response ->
                response.data.let response@{ mangaList ->
                    _mangaList.postValue(mangaList)
                    mangaList.forEach { manga ->
                        manga.relationships.filter { it.type == Relationship.AUTHOR }.forEach {
                            if (_authorsIds.size >= 100) return@response
                            _authorsIds.add(it.id)
                        }
                    }
                }
            }
        } else {
            Log.i(TAG, "mangaListResponse unsuccessful")
        }
    }

    private suspend fun loadAuthorList() {
        val authorListResponse = try {
            repository.mangaDexService.getAuthorList(_authorsIds)
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
            return
        } catch (e: IOException) {
            Log.e(TAG, "IOException, you might not have internet connection ")
            return
        }
        if (authorListResponse.isSuccessful) {
            Log.i(TAG, "authorListResponse is successful")
            authorListResponse.body()?.let {
                _authors.postValue(it.data)
            }
        } else {
            Log.i(TAG, "authorListResponse unsuccessful")
        }
    }

    companion object {
       private const val TAG = "MangaListViewModel.TAG"
    }

    class Factory(private val repository: RemoteRepositoryImpl) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = when(modelClass) {
                MangaListViewModel::class.java -> { MangaListViewModel(repository) }
                else -> { throw IllegalStateException("Unknown view model class") }
            }
            return viewModel as T
        }
    }
}