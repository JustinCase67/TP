package com.example.atelier2

import android.media.browse.MediaBrowser
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private val mp3Url = "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        playerView = findViewById(R.id.player_view)

    }

    override fun onStart() {
        super.onStart()

        // Création et configuration du player
        player = ExoPlayer.Builder(this).build().also { exo ->
            playerView.player = exo
        }

        // Construction du MediaItem
        val mediaItem = MediaItem.fromUri(mp3Url)
        player.setMediaItem(mediaItem)

        // Préparation et lancement de la lecture
        player.prepare()
        player.play()
    }

    override fun onStop() {
        super.onStop()

        // Libération des ressources du player
        player.release()
    }
}