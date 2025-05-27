package com.example.tp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlaylistActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_playlist)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val popFrancoBtn = findViewById<Button>(R.id.pop_btn)
        popFrancoBtn.setOnClickListener {
            returnGenre("Pop Franco")
        }
        val rapFrancoBtn = findViewById<Button>(R.id.rap_btn)
        rapFrancoBtn.setOnClickListener{
            returnGenre("Rap Franco")
        }

        val danceBtn = findViewById<Button>(R.id.dance_btn)
        danceBtn.setOnClickListener{
            returnGenre("Dance")
        }

        val rockBtn = findViewById<Button>(R.id.rock_btn)
        rockBtn.setOnClickListener{
            returnGenre("Rock")
        }

        val all = findViewById<Button>(R.id.all_btn)
        all.setOnClickListener{
            returnGenre("all")
        }


    }

    fun returnGenre(genre: String){
        val retour = Intent()
        retour.putExtra("genre", genre)
        setResult(RESULT_OK, retour)
        finish()
    }
}