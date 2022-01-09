package info.erulinman.mangadexreader.mangalist

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import info.erulinman.mangadexreader.api.NetworkResponse
import info.erulinman.mangadexreader.api.RemoteRepository
import info.erulinman.mangadexreader.api.entities.Author
import info.erulinman.mangadexreader.api.entities.Manga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

class MangaListViewModel @Inject constructor(
    private val repository: RemoteRepository
) : ViewModel() {

    private val _mangaList = MutableLiveData<List<Manga>>()
    val mangaList: LiveData<List<Manga>> = _mangaList

    private var authorsIds = mutableListOf<String>()

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _authors = MutableLiveData<List<Author>>()
    val authors: LiveData<List<Author>> = _authors

    fun fetchData(title: String) = viewModelScope.launch {
        authorsIds.clear()
        _loading.postValue(true)
        fetchManga(title)
        _loading.postValue(false)
    }

    private suspend fun fetchManga(title: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = repository.getMangaList(title)
        when (response) {
            is NetworkResponse.Success -> {
                _mangaList.postValue(response.data)
            }
            is NetworkResponse.Failure -> {
                // TODO
            }
        }
    }

    class Factory @Inject constructor(
        private val repository: RemoteRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = when(modelClass) {
                MangaListViewModel::class.java -> { MangaListViewModel(repository) }
                else -> { throw IllegalStateException("Unknown view model class") }
            }
            return viewModel as T
        }
    }
}