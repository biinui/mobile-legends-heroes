package com.neocicada.heroesofmobilelegends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.neocicada.heroesofmobilelegends.Genre.*
import com.neocicada.heroesofmobilelegends.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val musicAdapter: MusicAdapter by lazy {
        MusicAdapter()
    }

    companion object {
        private const val SPAN_COUNT = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.rvMusic.layoutManager = customGridLayoutManager()
        binding.rvMusic.adapter = musicAdapter
        musicAdapter.submitList(processData(musicList))
    }

    private fun processData(musicList: List<Music>): MutableList<MusicAdapter.ItemView> {
        val data = mutableListOf<MusicAdapter.ItemView>()
        values().forEach { genre ->
            data.add(MusicAdapter.ItemView.Header(genre))
            musicList.forEach { music ->
                if (music.genre == genre) data.add(MusicAdapter.ItemView.Music(music.name))
            }
        }
        return data
    }

    private fun customGridLayoutManager(): GridLayoutManager {
        return GridLayoutManager(this, SPAN_COUNT).also {
            it.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (musicAdapter.getItemViewType(position)) {
                        R.layout.item_music_header -> SPAN_COUNT
                        R.layout.item_music -> 1
                        else -> 1
                    }
                }
            }
        }
    }

}

val musicList = listOf(
    Music("1", Pop),
    Music("2", Pop),
    Music("3", Pop),
    Music("4", Rock),
    Music("5", Rock),
    Music("6", Rock),
    Music("7", Metal),
    Music("8", Metal),
    Music("9", Metal)
)

data class Music(
    val name: String,
    val genre: Genre
)

enum class Genre {
    Pop,
    Rock,
    Metal
}