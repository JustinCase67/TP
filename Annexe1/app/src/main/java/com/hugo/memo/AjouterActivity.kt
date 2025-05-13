package com.hugo.memo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class AjouterActivity : AppCompatActivity() {

    private lateinit var ajouter:Button
    private lateinit var memo:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ajouter = findViewById(R.id.ajouterAddBTN)
        memo = findViewById(R.id.memoET)

        val ec = Ecouteur()

        ajouter.setOnClickListener(ec)

    }

    inner class Ecouteur : View.OnClickListener {
        override fun onClick(v: View?) {
            if (v == ajouter) {
                val fos = openFileOutput("database.txt", MODE_APPEND)
                val osw = OutputStreamWriter(fos)
                val bw = BufferedWriter(osw)
                bw.write(memo.text.toString())
                bw.newLine()
                bw.close()
                finish()
            }
        }

    }

}