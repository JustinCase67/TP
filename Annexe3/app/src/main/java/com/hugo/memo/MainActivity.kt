package com.hugo.memo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var ajouter:Button
    private lateinit var afficher:Button
    private lateinit var quitter:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ajouter = findViewById(R.id.ajouterBTN)
        afficher = findViewById(R.id.afficherBTN)
        quitter = findViewById(R.id.quitterBTN)

        val ec = Ecouteur()

        ajouter.setOnClickListener(ec)
        afficher.setOnClickListener(ec)
        quitter.setOnClickListener(ec)

    }

    inner class Ecouteur: OnClickListener {
        override fun onClick(v: View?) {
            when (v) {
                ajouter -> {
                    val i = Intent(this@MainActivity, AjouterActivity::class.java)
                    startActivity(i)
                }
                afficher -> {
                    val i = Intent(this@MainActivity, AfficherActivity::class.java)
                    startActivity(i)
                }
                quitter -> { finish() }
            }
        }

    }

}