package com.example.tp2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var playlist: ListView

    val url = "https://api.jsonbin.io/v3/b/680a6a1d8561e97a5006b822?meta=false"
    val queue = Volley.newRequestQueue(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playlist = findViewById(R.id.list)

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // 1. On extrait le tableau “music”
                val array = response.getJSONArray("music")
                val chansons = mutableListOf<Chanson>()

                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    val chanson = Chanson.builder()
                        .cid(obj.optString("id"))
                        .title(obj.optString("title"))
                        .album(obj.optString("album"))
                        .artist(obj.optString("artist"))
                        .genre(obj.optString("genre"))
                        .source(obj.optString("source"))
                        .img(obj.optString("image"))
                        .trackNumber(obj.optInt("trackNumber"))
                        .totalTrackCount(obj.optInt("totalTrackCount"))
                        .duration(obj.optInt("duration"))
                        .site(obj.optString("site"))
                        .build()
                    chansons.add(chanson)
                }

                // 2. Affichage dans le ListView
                val titres = chansons.map { it.title ?: "Titre inconnu" }
                playlist.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    titres
                )
            },
            { error ->
                Toast.makeText(
                    this,
                    "Erreur : ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        )



    }
}