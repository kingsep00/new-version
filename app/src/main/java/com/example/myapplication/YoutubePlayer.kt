package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.youtube.player.YouTubePlayerView
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.internal.t
import android.util.Log
import android.widget.Toast


class YoutubePlayer : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private val TAG = "MainActivity"
    private val REQ_CODE = 123

    lateinit var youtubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtubeplayer)

        youtubePlayerView = findViewById(R.id.youtubeplayer)
        youtubePlayerView.initialize(getString(R.string.api_key), this)
    }

    override fun onInitializationSuccess(
            provider: YouTubePlayer.Provider?,
            youtubePlayer: YouTubePlayer?,
            wasRestored: Boolean
    ) {
        val videoId = "7iBmZbSnI-E"
        if (!wasRestored) {
            youtubePlayer?.loadVideo(videoId)
        } else {
            youtubePlayer?.play()
        }
    }

    override fun onInitializationFailure(
            provider: YouTubePlayer.Provider?,
            result: YouTubeInitializationResult?
    ) {
        if (result?.isUserRecoverableError == true) {
            result.getErrorDialog(this, REQ_CODE).show()
        } else {
            val errorMessage = "There was an error initializing the YouTubePlayer ($result)"
            Log.e(TAG, errorMessage)
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}