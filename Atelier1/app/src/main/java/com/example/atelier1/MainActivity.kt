package com.example.atelier1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
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
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response -> Toast.makeText(this, response, Toast.LENGTH_LONG).show() },
            { error -> Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show() }
        )
        queue.add(stringRequest)
    }
}