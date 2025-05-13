package com.example.annexe5



import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class MainActivity : AppCompatActivity() {

    private val titres = arrayOf("Chanson A", "Chanson B", "Chanson C")
    private val images = intArrayOf(R.drawable.chanson1, R.drawable.chanson2, R.drawable.chanson3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listeView = findViewById<ListView>(R.id.listeChansons)

        val data = ArrayList<Map<String, Any>>()
        for (i in titres.indices) {
            val item = HashMap<String, Any>()
            item["titre"] = titres[i]
            item["image"] = images[i]
            data.add(item)
        }

        val from = arrayOf("titre", "image")
        val to = intArrayOf(R.id.texteTitre, R.id.imageChanson)

        val adapter = SimpleAdapter(this, data, R.layout.item_chanson, from, to)
        listeView.adapter = adapter

        listeView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Vous avez cliqué: ${titres[position]}", Toast.LENGTH_SHORT).show()
        }
    }
}