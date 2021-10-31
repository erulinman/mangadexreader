package info.erulinman.mangadexreader.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.erulinman.mangadexreader.databinding.ItemMangaListBinding
import info.erulinman.mangadexreader.model.entities.Author
import info.erulinman.mangadexreader.model.entities.DefaultData
import info.erulinman.mangadexreader.model.entities.MangaAttributes
import info.erulinman.mangadexreader.model.entities.Relationship

class MangaListAdapter : ListAdapter<DefaultData<MangaAttributes>, MangaListAdapter.MangaViewHolder>(MangaDiffCallback) {
    private var authors: List<Author>? = null

    fun loadAuthors(authors: List<Author>) {
        Log.i(TAG, "MangaListAdapter.loadAuthors(): $authors")
        this.authors = authors
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        return MangaViewHolder(
            ItemMangaListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga = getItem(position)
        val mangaTitle: String = manga.attributes.title.let { titleList ->
            if (titleList.keys.contains(BASE_LANG)) {
                return@let titleList.getOrDefault(BASE_LANG, "(Empty name)")
            } else if (titleList.keys.isNotEmpty()) {
                return@let titleList.values.first()
            }
            return@let "(Empty name)"
        }
        val mangaAuthors = authors?.let { authors ->
            val outputString = ""
            manga.relationships.filter { it.type == Relationship.AUTHOR }.forEach { currentMangaAuthor ->
                authors.forEach { loadedAuthor ->
                    if (currentMangaAuthor.id == loadedAuthor.id) {
                        outputString.plus(loadedAuthor.attributes.name)
                    }
                }
            }
            return@let outputString
        } ?: run {
            Log.i(TAG, "Authors = null")
            return@run null
        }

        holder.binding.apply {
            title.text = mangaTitle
            author.text = mangaAuthors
        }
    }

    inner class MangaViewHolder(val binding: ItemMangaListBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val TAG = "MangaListAdapter.TAG"
        private const val BASE_LANG = "en"
    }
}

object MangaDiffCallback : DiffUtil.ItemCallback<DefaultData<MangaAttributes>>() {
    override fun areItemsTheSame(oldItem: DefaultData<MangaAttributes>, newItem: DefaultData<MangaAttributes>): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DefaultData<MangaAttributes>, newItem: DefaultData<MangaAttributes>): Boolean {
        return oldItem == newItem
    }
}