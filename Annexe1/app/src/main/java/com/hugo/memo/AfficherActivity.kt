package com.hugo.memo

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

class AfficherActivity : AppCompatActivity() {

    private lateinit var list:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_afficher)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        list = findViewById(R.id.memosLV)

        try {

            val fis = openFileInput("database.txt")
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)

            br.use {
                val temp = br.readLines()
                list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, temp)

                //annexe 1b?
                println(temp.size)

                var count = 0
                for (l in temp){
                    count += l.length
                }
                println(count)

                count = 0
                for (l in temp){
                    for (c in l){
                        if (c == 'c' || c == 'C') {
                            count++
                        }
                    }
                }
                println(count)

            }

            //br.close()




        } catch (fnfe:FileNotFoundException) {
            fnfe.printStackTrace()
        }

    }

}