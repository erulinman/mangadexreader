package info.erulinman.mangadexreader.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import info.erulinman.mangadexreader.model.Repository
import java.lang.IllegalStateException

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass) {
            MangaListViewModel::class.java -> { MangaListViewModel(repository) }
            else -> { throw IllegalStateException("Unknown view model class") }
        }
        return viewModel as T
    }
}