package info.erulinman.mangadexreader.mangalist

import androidx.lifecycle.*
import info.erulinman.mangadexreader.api.MangaRepository
import info.erulinman.mangadexreader.api.NetworkResponse
import info.erulinman.mangadexreader.api.entities.Manga
import info.erulinman.mangadexreader.utils.DataState
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

class MangaListViewModel @Inject constructor(
    private val repository: MangaRepository
) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<List<Manga>>>(DataState.Empty)
    val dataState: LiveData<DataState<List<Manga>>> = _dataState

    fun fetchManga(title: String) = viewModelScope.launch {
        _dataState.postValue(DataState.Loading)
        when (val response = repository.getMangaList(title)) {
            is NetworkResponse.Success -> {
                val value = DataState.Loaded(response.data)
                _dataState.postValue(value)
            }
            is NetworkResponse.Failure -> {
                val value = DataState.Error(response.exception.toString())
                _dataState.postValue(value)
            }
        }
    }

    class Factory @Inject constructor(
        private val repository: MangaRepository
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