package com.example.annexe7

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var photoView: ImageView
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

            val callButton: Button = findViewById(R.id.btn_call)
            val mapButton: Button    = findViewById(R.id.btn_map)
            val cameraButton: Button = findViewById(R.id.btn_camera)
            val shareButton: Button  = findViewById(R.id.btn_share)
            photoView               = findViewById(R.id.iv_photo)

            // 1) Appel téléphonique à Marie
            callButton.setOnClickListener {
                val telUri = Uri.parse("tel:+11234567890") // Remplacez par le numéro de Marie
                val dialIntent = Intent(Intent.ACTION_DIAL, telUri)
                startActivity(dialIntent)
            }

            // 2) Afficher la carte de Hawkesbury
            mapButton.setOnClickListener {
                val geoUri = Uri.parse("geo:0,0?q=Hawkesbury,+ON,+Canada")
                val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
                startActivity(mapIntent)
            }

            // 3) Prendre une photo et recevoir le résultat
            takePictureLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                    imageBitmap?.let { photoView.setImageBitmap(it) }
                }
            }
            cameraButton.setOnClickListener {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (cameraIntent.resolveActivity(packageManager) != null) {
                    takePictureLauncher.launch(cameraIntent)
                }
            }

            // 7) BONUS: Partager du texte via Sharesheet
            shareButton.setOnClickListener {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, "Bonjour! Voici un message à partager.")
                }
                startActivity(Intent.createChooser(shareIntent, "Partager via"))
            }
        }
    }

    /*
     * Question:
     * Est-ce que l’interface mise en œuvre pour le retour du boomerang
     * (ActivityResultCallback) est une interface fonctionnelle?
     *
     * Réponse:
     * Oui: ActivityResultCallback<T> ne déclare qu'une seule méthode callback(T result).
     * C'est une interface fonctionnelle, on peut donc utiliser une expression lambda
     * pour la simplifier comme montré ci-dessus.
     */
}