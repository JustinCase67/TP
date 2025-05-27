package com.example.tp

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class DossierChanson(context: Context) {

    private val queue = Volley.newRequestQueue(context)
    private val klaxon = Klaxon()
    private val observers = mutableListOf<(List<Chanson>) -> Unit>()

    data class ReponseMusic(val music: List<Chanson>)


    fun addObserver(observer: (List<Chanson>) -> Unit) {
        observers.add(observer)
    }

    fun recevoirChansons(webUrl: String, music_genre:String = "all") {
        val request = StringRequest(
            Request.Method.GET, webUrl,
            { response ->
                val playlist_complète = klaxon.parse<ReponseMusic>(response)?.music
                val chansons : List<Chanson>?
                if(music_genre != "all") {
                    chansons = playlist_complète?.filter { it.genre.equals(music_genre, true) }
                }
                else {
                    chansons = playlist_complète
                }
                observers.forEach {
                    if (chansons != null) {
                        it(chansons)
                    }
                }
            },
            { error -> error.printStackTrace()}
        )
        queue.add(request)
    }
}