package com.example.atelier3_kotlin

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var texte: TextView
    private lateinit var leModele: Sujet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        texte = findViewById(R.id.texte)

    }

    override fun onStart() {
        super.onStart()
        leModele = Modele()
        (leModele as Modele).ajouterObservateur(this)
    }

    override fun changement(valeur: Int) {
        texte.text = "nouvelle valeur : $valeur"
    }

    override fun onDestroy() {
        super.onDestroy()
        leModele.enleverObservateur(this)
    }
}