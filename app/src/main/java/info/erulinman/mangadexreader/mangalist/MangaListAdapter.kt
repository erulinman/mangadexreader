package info.erulinman.mangadexreader.mangalist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.erulinman.mangadexreader.databinding.ItemMangaListBinding
import info.erulinman.mangadexreader.api.entities.*

class MangaListAdapter :
    ListAdapter<Manga, MangaListAdapter.MangaViewHolder>(DiffUtilCallback()){

    class MangaViewHolder(
        private val binding: ItemMangaListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(manga: Manga) {
            binding.title.text = manga.attributes.title.let { titles ->
                if (titles.isEmpty()) return@let EMPTY_NAME
                titles.getOrElse(BASE_LANG) {
                    titles.values.first()
                }
            }
        }
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
        holder.bind(currentList[position])
    }

    companion object {
        private const val BASE_LANG = "en"
        private const val EMPTY_NAME = "Unknown"
    }
}