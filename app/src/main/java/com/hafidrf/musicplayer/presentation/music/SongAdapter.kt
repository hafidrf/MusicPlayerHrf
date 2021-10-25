package com.hafidrf.musicplayer.presentation.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hafidrf.musicplayer.databinding.ItemRowMusicBinding
import com.hafidrf.musicplayer.domain.entity.MusicEntity
import com.hafidrf.musicplayer.utils.gone
import com.hafidrf.musicplayer.utils.visible

class SongAdapter() : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var mSongs = listOf<MusicEntity>()
    private var mListener: OnSongClickedListener? = null

    inner class SongViewHolder(val binding: ItemRowMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(song: MusicEntity, position: Int) = with(binding) {
            Glide.with(image.context)
                .load(song.artWorkUrl)
                .circleCrop()
                .into(image)

            tvSongName.text = song.songName
            tvAlbumName.text = song.albumName
            tvArtistName.text = song.artisName

            root.setOnClickListener {
                mListener?.onClicked(position, song) {
                    animationView.visible()
                    if (it) animationView.playAnimation() else animationView.pauseAnimation()
                }
            }

            animationView.gone()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            ItemRowMusicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bindItem(mSongs[position], position)
    }

    override fun getItemCount(): Int {
        return mSongs.size
    }

    fun setData(data: List<MusicEntity>) {
        mSongs = data
        notifyDataSetChanged()
    }

    fun setListener(listener: OnSongClickedListener) {
        mListener = listener
    }

    interface OnSongClickedListener {
        fun onClicked(position: Int, song: MusicEntity, callback: (Boolean) -> Unit)
    }

}