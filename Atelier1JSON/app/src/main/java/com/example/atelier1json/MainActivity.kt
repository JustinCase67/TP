package com.example.atelier1json

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.jsonbin.io/v3/b/67fe6a908a456b796689f63d?meta=false"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val produits = mutableListOf<Produit>()
                for (i in 0 until response.length()) {
                    val obj = response.getJSONObject(i)
                    produits.add(
                        Produit(
                            obj.getInt("id"),
                            obj.getString("nom"),
                            obj.getDouble("prix")
                        )
                    )
                }
                val listView: ListView = findViewById(R.id.listView)
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    produits.map { "${it.nom} - €${it.prix}" }
                )
                listView.adapter = adapter
                listView.setOnItemClickListener { _, _, pos, _ ->
                    Toast.makeText(
                        this,
                        "Prix: €${produits[pos].prix}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            { error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show() }
        )
        queue.add(jsonArrayRequest)
    }
}