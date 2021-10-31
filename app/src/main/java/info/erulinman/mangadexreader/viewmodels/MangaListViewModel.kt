package info.erulinman.mangadexreader.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.erulinman.mangadexreader.model.Repository
import info.erulinman.mangadexreader.model.entities.Author
import info.erulinman.mangadexreader.model.entities.Manga
import info.erulinman.mangadexreader.model.entities.Relationship
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MangaListViewModel(private val repository: Repository) : ViewModel() {

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

    fun getMangaListByTitle(title: String) {
        _mangaList.value = mutableListOf()
        _authorsIds = mutableListOf()
        _loading.value = true

        // Get list of manga
        viewModelScope.launch(Dispatchers.IO) {
            val mangaListResponse = try {
                repository.mangaDexService.getMangaListByTitle(title)
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launch
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection ")
                return@launch
            }
            if (mangaListResponse.isSuccessful) {
                Log.i(TAG, "mangaListResponse is successful")
                mangaListResponse.body()?.let { response ->
                    response.data.let { mangaList ->
                        _mangaList.postValue(mangaList)
                        mangaList.forEach { manga ->
                            manga.relationships.filter { it.type == Relationship.AUTHOR }.forEach {
                                _authorsIds.add(it.id)
                            }
                        }
                    }
                }
            } else {
                Log.i(TAG, "mangaListResponse unsuccessful")
            }
            val authorListResponse = try {
                Log.i(TAG, "_authorIds = $_authorsIds")
                Log.i(TAG, "_authorIds.size = ${_authorsIds.size}")
                repository.mangaDexService.getAuthorList(_authorsIds)
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launch
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection ")
                return@launch
            }
            if (authorListResponse.isSuccessful) {
                Log.i(TAG, "authorListResponse is successful")
                authorListResponse.body()?.let {
                    _authors.postValue(it.data)
                    Log.i(
                        TAG,
                        "authorListResponse Body:\n" +
                                "Result: ${it.result}\n" +
                                "Total: ${it.total}\n" +
                                "Body list size: ${it.data.size}\n" +
                                "Authors: ${it.data}"
                    )
                }
            } else {
                Log.i(TAG, "authorListResponse unsuccessful")
            }
            _loading.postValue(false)
        }
    }

    companion object {
       private const val TAG = "MangaListViewModel.TAG"
    }
}