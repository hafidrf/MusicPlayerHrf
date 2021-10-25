package com.hafidrf.musicplayer.presentation.music

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hafidrf.musicplayer.R
import com.hafidrf.musicplayer.databinding.ActivitySearchMusicBinding
import com.hafidrf.musicplayer.domain.entity.MusicEntity
import com.hafidrf.musicplayer.presentation.base.BaseViewBindingActivity
import com.hafidrf.musicplayer.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException

class SearchMusicActivity : BaseViewBindingActivity<ActivitySearchMusicBinding>(ActivitySearchMusicBinding::inflate), MediaPlayer.OnPreparedListener {

    private val vm: SearchMusicViewModel by viewModel()
    private val mSongAdapter by lazy { SongAdapter() }
    private var currentSongIndex: Int? = null

    private var mediaPlayer: MediaPlayer? = null
    private val mHandler = Looper.myLooper()?.let { Handler(it) }
    private var mCallbackPlayAnimation: ((Boolean) -> Unit)? = null
    private var isSongPaused = false


    private lateinit var appDialog: AppLoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initMediaPlayer()
        appDialog = AppLoadingDialog(this)

        observeSearchResult()
    }

    private fun initViews(){
        initRv()
        initSeekBar()

        binding.inputSearchArtist.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                vm.searchMusic(binding.inputSearchArtist.text.toString())

                // Only runs if there is a view that is currently focused
                this.currentFocus?.let { view ->
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
            true
        }

        binding.btnPause.setOnClickListener {
            if (isSongPaused){
                resumeSongUi()
                mediaPlayer?.start()
            } else {
                pauseSongUi()
                mediaPlayer?.pause()
            }
        }
    }

    private fun observeSearchResult(){
        vm.musicResult.observe(this, { state -> when(state) {
            is UiState.Loading -> {
                binding.progressBar.visible()
                binding.tvInitState.gone()
                if (mediaPlayer?.isPlaying == true){
                    pauseSongUi()
                    mediaPlayer?.pause()
                    binding.layoutPlaying.gone()
                    mSongAdapter.setData(listOf())
                }
            }
            is UiState.Success -> {
                mSongAdapter.setData(state.data)
                binding.progressBar.gone()
                if (state.data.isEmpty()){
                    binding.tvInitState.visible()
                    binding.tvInitState.text = "Song / artist not found."
                }
            }
            is UiState.Error -> {
                binding.progressBar.gone()
                Toast.makeText(this, state.throwable.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        } })
    }

    private fun initRv(){
        binding.rvSongs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mSongAdapter
        }

        mSongAdapter.setListener(object : SongAdapter.OnSongClickedListener {
            override fun onClicked(position: Int, song: MusicEntity, callback: (Boolean) -> Unit) {
                currentSongIndex?.let { mSongAdapter.notifyItemChanged(it) }
                appDialog.show()
                playSong(song.musicUrl)
                setUiPlayer(song)
                mCallbackPlayAnimation = callback
                currentSongIndex = position
            }
        })
    }

    private fun initMediaPlayer(){
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setOnPreparedListener(this@SearchMusicActivity)
            setOnCompletionListener {
                pauseSongUi()
            }
        }
    }

    private fun pauseSongUi(){
        isSongPaused = true
        binding.btnPause.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        mCallbackPlayAnimation?.let { it(false) }
    }

    private fun resumeSongUi(){
        isSongPaused = false
        binding.btnPause.setImageResource(R.drawable.ic_baseline_pause_24)
        mCallbackPlayAnimation?.let { it(true) }
    }

    fun playSong(songUrl: String){
        try {
            mediaPlayer?.reset()
            mediaPlayer?.setDataSource(songUrl)
            mediaPlayer?.prepareAsync()

        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun setUiPlayer(song: MusicEntity){
        binding.apply {
            Glide.with(this@SearchMusicActivity)
                .load(song.artWorkUrl)
                .into(imgSong)

            tvSongName.text = song.songName
            tvArtistName.text = song.artisName
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        if (mp != null) {

            mp.start()
            appDialog.dismiss()

            binding.apply {
                divider.visible()
                layoutPlaying.visible()
                btnPause.setImageResource(R.drawable.ic_baseline_pause_24)
                seekBar.progress = 0
                seekBar.max = 100
            }

            updateProgressBar()
            resumeSongUi()
        }
    }

    /**
     * Update timer on seekbar
     */
    fun updateProgressBar() {
        mHandler?.postDelayed(mUpdateTimeTask, 100)
    }

    private fun initSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mHandler?.removeCallbacks(mUpdateTimeTask)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mHandler?.removeCallbacks(mUpdateTimeTask)
                val totalDuration = mediaPlayer?.duration
                val currentPosition = MpUtils.progressToTimer(seekBar?.progress!!.toInt(), totalDuration!!)

                // forward or backward to certain seconds
                mediaPlayer?.seekTo(currentPosition)

                // update timer progress again
                updateProgressBar()
            }

        })
    }

    /**
     * Background Runnable thread
     */
    private val mUpdateTimeTask = object : Runnable {
        override fun run() {
            val totalDuration = mediaPlayer?.duration?.toLong()
            val currentDuration = mediaPlayer?.currentPosition?.toLong()

            if (totalDuration != null && currentDuration != null){
                binding.tvTime.text = "${MpUtils.milliSecondsToTimer(currentDuration)} / ${MpUtils.milliSecondsToTimer(totalDuration)}"

                // Updating progress bar
                val progress = MpUtils.getProgressPercentage(currentDuration, totalDuration)
                binding.seekBar.progress = progress
            }

            // Running this thread after 100 milliseconds
            mHandler?.postDelayed(this, 100)
        }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}