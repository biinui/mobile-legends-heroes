package com.neocicada.heroesofmobilelegends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neocicada.heroesofmobilelegends.databinding.ItemMusicBinding
import com.neocicada.heroesofmobilelegends.databinding.ItemMusicHeaderBinding

class MusicAdapter : ListAdapter<MusicAdapter.ItemView, RecyclerView.ViewHolder>(MusicDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ItemView.Header -> R.layout.item_music_header
            is ItemView.Music -> R.layout.item_music
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_music_header -> HeaderViewHolder.from(parent)
            R.layout.item_music -> MusicViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val itemView = getItem(position)) {
            is ItemView.Header -> (holder as HeaderViewHolder).bind(itemView)
            is ItemView.Music -> (holder as MusicViewHolder).bind(itemView)
        }
    }

    class MusicViewHolder(
        private val binding: ItemMusicBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): MusicViewHolder {
                return MusicViewHolder(
                    ItemMusicBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(music: ItemView.Music) {
            binding.tvName.text = music.name
        }

    }

    class HeaderViewHolder(
        private val binding: ItemMusicHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                return HeaderViewHolder(
                    ItemMusicHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(header: ItemView.Header) {
            binding.tvGenre.text = header.genre.toString()
        }

    }

    sealed class ItemView {
        data class Header(val genre: Genre) : ItemView()
        data class Music(val name: String) : ItemView()
    }

}

fun MusicAdapter.ItemView.isContentsSameWith(other: MusicAdapter.ItemView): Boolean {
    return if (this is MusicAdapter.ItemView.Header && other is MusicAdapter.ItemView.Header) this.genre == other.genre
    else if (this is MusicAdapter.ItemView.Music && other is MusicAdapter.ItemView.Music) this.name == other.name
    else false
}

class MusicDiffUtil : DiffUtil.ItemCallback<MusicAdapter.ItemView>() {

    override fun areItemsTheSame(oldItem: MusicAdapter.ItemView, newItem: MusicAdapter.ItemView): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MusicAdapter.ItemView, newItem: MusicAdapter.ItemView): Boolean {
        return oldItem.isContentsSameWith(newItem)
    }

}
