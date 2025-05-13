package com.example.tp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var playlist: ListView
    val url = "https://api.jsonbin.io/v3/b/680a6a1d8561e97a5006b822?meta=false"
    private lateinit var player: ExoPlayer
    private lateinit var dossier: DossierChanson

    private var songs: List<Chanson> = emptyList()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuration des boutons
        findViewById<Button>(R.id.btn_play).setOnClickListener { player.play() }
        findViewById<Button>(R.id.btn_pause).setOnClickListener { player.pause() }
        findViewById<Button>(R.id.btn_next).setOnClickListener { playNext() }
        findViewById<Button>(R.id.btn_prev).setOnClickListener { playPrevious() }
        findViewById<Button>(R.id.btn_skip10).setOnClickListener {
            player.seekTo(player.currentPosition + 10_000)
        }

        player = ExoPlayer.Builder(this).build()

        // Chargement des chansons
        dossier = DossierChanson(this)
        dossier.addObserver { loadedSongs ->
            songs = loadedSongs
            if (songs.isNotEmpty()) {
                prepareAndPlay(0)
            }
        }
        dossier.recevoirChansons("https://api.jsonbin.io/v3/b/680a6a1d8561e97a5006b822?meta=false")
    }

    private fun prepareAndPlay(index: Int) {
        currentIndex = index
        val mediaItem = MediaItem.fromUri(songs[index].source!!)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    private fun playNext() {
        if (songs.isNotEmpty()) {
            val next = (currentIndex + 1) % songs.size
            prepareAndPlay(next)
        }
    }

    private fun playPrevious() {
        if (songs.isNotEmpty()) {
            val prev = (currentIndex - 1 + songs.size) % songs.size
            prepareAndPlay(prev)
        }
    }

    override fun onStart() {
        super.onStart()
        // Pas besoin de re-preparer ici, ExoPlayer tourne toujours
    }

    override fun onStop() {
        super.onStop()
        // Conserver le player actif pour la rotation
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}