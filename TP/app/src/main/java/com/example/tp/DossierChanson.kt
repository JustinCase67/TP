package com.example.tp

import android.R
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import org.json.JSONObject
import java.io.StringReader
import android.content.Context

class DossierChanson(context: Context) {

    private val queue = Volley.newRequestQueue(context)
    private val klaxon = Klaxon()
    private val observers = mutableListOf<(List<Chanson>) -> Unit>()

    fun addObserver(observer: (List<Chanson>) -> Unit) {
        observers += observer
    }

    fun recevoirChansons(webUrl: String) {
        val request = JsonObjectRequest(
            Request.Method.GET, webUrl, null,
            { response ->
                val root = Parser.default().parse(StringBuilder(response.toString())) as? JsonObject
                val array = root?.get("music") as? com.beust.klaxon.JsonArray<*>
                val chansons = array?.let { Klaxon().parseFromJsonArray<Chanson>(it) }

                observers.forEach {
                    if (chansons != null) {
                        it(chansons)
                    }
                }
            },
            { error ->
                error.printStackTrace()
            }
        )
        queue.add(request)
    }
}