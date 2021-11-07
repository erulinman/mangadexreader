package info.erulinman.mangadexreader.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.erulinman.mangadexreader.databinding.ItemMangaListBinding
import info.erulinman.mangadexreader.model.entities.*

class MangaListAdapter : RecyclerView.Adapter<MangaListAdapter.MangaViewHolder>() {

    private var mangaList: List<Manga>? = null
    private var authorList: List<Author>? = null

    fun setAuthorList(_authors: List<Author>) {
        authorList = _authors
    }

    fun setMangaList(_mangaList: List<Manga>) {
        mangaList = _mangaList
    }

    class MangaViewHolder(val binding: ItemMangaListBinding) :
        RecyclerView.ViewHolder(binding.root)

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
        val manga = mangaList!![position]
        val title = manga.attributes.title.let { titleList ->
            if (titleList.keys.contains(BASE_LANG)) {
                return@let titleList.getOrDefault(BASE_LANG, "(Empty name)")
            } else if (titleList.keys.isNotEmpty()) {
                return@let titleList.values.first()
            }
            return@let "(Empty name)"
        }
        val author = authorList?.let { authorList ->
            var outputString = ""
            manga.relationships.filter { it.type == Relationship.AUTHOR }.forEach { currentMangaAuthor ->
                Log.d(TAG, "$currentMangaAuthor")
                authorList.forEach { loadedAuthor ->
                    if (currentMangaAuthor.id == loadedAuthor.id) {
                        val name = loadedAuthor.attributes.name
                        outputString = if (outputString.isEmpty()) name else "$outputString, $name"
                    }
                }
            }
            return@let outputString
        }

        holder.binding.apply {
            Log.d(TAG, "title: $title")
            this.title.text = title
            Log.d(TAG, "author: $author")
            this.author.text = author
        }
    }

    override fun getItemCount() = mangaList?.size ?: 0

    companion object {
        private const val TAG = "MangaListAdapter.TAG"
        private const val BASE_LANG = "en"
    }
}