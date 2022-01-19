package info.erulinman.mangadexreader.mangalist

import androidx.recyclerview.widget.DiffUtil
import info.erulinman.mangadexreader.api.entities.Manga

class DiffUtilCallback: DiffUtil.ItemCallback<Manga>() {

    override fun areItemsTheSame(oldItem: Manga, newItem: Manga) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Manga, newItem: Manga) =
        oldItem == newItem
}