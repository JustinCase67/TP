package com.example.annexe4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IdentificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_identification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val champPrenom = findViewById<EditText>(R.id.champPrenom)
        val champNom = findViewById<EditText>(R.id.champNom)
        val boutonConfirmer = findViewById<Button>(R.id.boutonConfirmer)

        boutonConfirmer.setOnClickListener {
            val prenom = champPrenom.text.toString()
            val nom = champNom.text.toString()
            val utilisateur = Utilisateur(prenom, nom)

            val retour = Intent()
            retour.putExtra("util",utilisateur)
            setResult(RESULT_OK,retour)
            finish()
        }
    }
}