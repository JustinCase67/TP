package com.example.tp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var seekBar: SeekBar
    private lateinit var repository: DossierChanson

    private var songs = listOf<Chanson>()
    private var currentIndex = 0
    private val url = "https://api.jsonbin.io/v3/b/680a6a1d8561e97a5006b822?meta=false"

    private val handler = Handler()
    private val updateSeek = object : Thread() {
        override fun run() {
            handler.post{
                seekBar.progress = player.currentPosition.toInt()
            }
            handler.postDelayed(this, 1000)
        }
    }

    private var isShuffle = false
    private var isRepeat = false
    private var isPlaying = true
    lateinit var lanceur : ActivityResultLauncher<Intent>
    private var genre = "all"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        player = ExoPlayer.Builder(this).build()
        playerView = findViewById(R.id.player_view)
        playerView.player = player
        playerView.useController = false


        //QUAND LE BOUTON CHANGE SELON LE STATUT DU PLAYER
        val playerbtn  = findViewById<ImageButton>(R.id.btn_play)
        playerbtn.setOnClickListener {
            if(isPlaying){
                playerbtn.setImageResource(R.drawable.play)
                player.pause()
                handler.removeCallbacks(updateSeek)
                isPlaying = !isPlaying
            }
            else{
                playerbtn.setImageResource(R.drawable.pause)
                player.play()
                handler.post(updateSeek)
                isPlaying = !isPlaying
            }
        }
        val skip10Btn = findViewById<ImageButton>(R.id.btn_skip10)
        skip10Btn.setOnClickListener {
            player.seekTo(player.currentPosition + 10000)
        }
        val back10Btn = findViewById<ImageButton>(R.id.btn_back10)
        back10Btn.setOnClickListener {
            player.seekTo(player.currentPosition - 10000)
        }
        val prevBtn = findViewById<ImageButton>(R.id.btn_prev)
        prevBtn.setOnClickListener {
            navigation(-1)
        }
        val nextBtn = findViewById<ImageButton>(R.id.next_btn)
        nextBtn.setOnClickListener {
           navigation(1)
        }
        var shuffleBtn  = findViewById<ImageButton>(R.id.btn_shuffle)
        shuffleBtn.setOnClickListener {
            isShuffle = !isShuffle
            if (isShuffle){
                shuffleBtn.setImageResource(R.drawable.green_shuffle)
            }
            else
                shuffleBtn.setImageResource(R.drawable.shuffle)

            player.shuffleModeEnabled = isShuffle

        }
        val repeatBtn = findViewById<ImageButton>(R.id.btn_repeat)
        repeatBtn.setOnClickListener {
            isRepeat = !isRepeat
            if (isRepeat){
                repeatBtn.setImageResource(R.drawable.repeat_green)
                player.repeatMode = Player.REPEAT_MODE_ONE
            }
            else {
                repeatBtn.setImageResource(R.drawable.repeat)
                Player.REPEAT_MODE_OFF
            }
        }

        val playlistBtn = findViewById<ImageButton>(R.id.playlist_btn)
        playlistBtn.setOnClickListener{
            lanceur.launch(Intent(this@MainActivity,PlaylistActivity::class.java))
        }

        lanceur = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            CallBackPlaylist()
        )





        findViewById<TextView>(R.id.tv_title)
        findViewById<TextView>(R.id.tv_artist)
        seekBar = findViewById(R.id.seekBar)

        val ec = Ecouteur()

        seekBar.setOnSeekBarChangeListener(ec)

        val listView = findViewById<ListView>(R.id.list_songs)
        listView.setOnItemClickListener { _, _, pos, _ ->
            prepareAndPlay(pos)
        }

        repository = DossierChanson(this)
        repository.addObserver { values ->
            songs = values
            runOnUiThread {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_2,
                    android.R.id.text1,
                    songs.map { "${it.title} — ${it.artist}" }
                )
                listView.adapter = adapter
                if (!songs.isEmpty()) {
                    prepareAndPlay(0)
                }
            }
        }
        repository.recevoirChansons(url, genre)
    }

    private fun navigation(step: Int) {
        if (songs.isEmpty())
            return
       if (isShuffle) {
           currentIndex =  Random.nextInt(songs.size)
        } else {
           currentIndex = (currentIndex + step + songs.size) % songs.size
        }
        prepareAndPlay(currentIndex)
    }

    inner class Ecouteur : OnSeekBarChangeListener {
        override fun onProgressChanged(sb: SeekBar, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                player.seekTo(progress.toLong())
            }
        }
        override fun onStartTrackingTouch(sb: SeekBar) {
            handler.removeCallbacks(updateSeek)
        }
        override fun onStopTrackingTouch(sb: SeekBar) {
            handler.post(updateSeek)
        }
    }


    private fun prepareAndPlay(index: Int) {
        currentIndex = index
        val song = songs[index]

        findViewById<TextView>(R.id.tv_title).text  = song.title
        findViewById<TextView>(R.id.tv_artist).text = song.artist

        player.stop()
        player.clearMediaItems()

        player.setMediaItem(MediaItem.fromUri(song.source!!))

        player.prepare()
        player.play()

        seekBar.max = player.duration.toInt()
        seekBar.progress = 0
        handler.post(updateSeek)
    }

    inner class CallBackPlaylist : ActivityResultCallback<ActivityResult> {
        override fun onActivityResult(result : ActivityResult)
        {
            if(result.resultCode == RESULT_OK)
            {
                val intentRetour = result.data
                if(intentRetour != null)
                {
                    genre = intentRetour.getStringExtra("genre").toString()
                    repository.recevoirChansons(url, genre)
                }
            }

        }

    }


    override fun onStart() {
        super.onStart()
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    val dur = player.duration
                    if (dur > 0) {
                        seekBar.max = dur.toInt()
                    }
                    handler.post(updateSeek)
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(updateSeek)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateSeek)
        player.release()
    }
}

