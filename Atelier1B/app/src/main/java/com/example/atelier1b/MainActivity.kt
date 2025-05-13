package com.example.atelier1b

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

// 1. Ajoutez la dépendance Klaxon dans votre module app/build.gradle :
// implementation 'com.beust:klaxon:5.0.1'

// 2. Créez les modèles de données dans src/main/java/com/monpackage/models/Models.kt :


// Représente un produit avec son nom et son prix
data class Produit(
    val nom: String,
    val prix: Double
)

// Wrapper pour la liste de produits, la clé JSON "produits" est mappée grâce à l'annotation @Json
data class ListeProduits(
    @Json(name = "produits") val produits: List<Produit>
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.list)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Référence au ListView défini dans activity_main.xml
        val listView: ListView = findViewById(R.id.list)
        val url = "https://api.jsonbin.io/v3/b/67fe6a908a456b796689f63d?meta=false"

        // Initialisation de la queue Volley
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // 4. Parser la réponse JSON en objet Kotlin
                val listeProduits = Klaxon().parse<ListeProduits>(response)

                // 5. Vérification dans la console
                listeProduits?.produits?.forEach { produit ->
                    Log.d("MainActivity", "Nom: ${produit.nom}, Prix: ${produit.prix}")
                }

                // 6. Transformer la liste en ArrayList de Maps pour SimpleAdapter
                val dataList = ArrayList<Map<String, String>>()
                listeProduits?.produits?.forEach { p ->
                    val item = HashMap<String, String>()
                    item["nom"] = p.nom
                    item["prix"] = p.prix.toString()
                    dataList.add(item)
                }

                // Définir quelles clés de la Map vont dans quels TextViews
                val from = arrayOf("nom", "prix")
                val to = intArrayOf(R.id.textNom, R.id.textPrix)

                // Création et association de l'adaptateur
                val adapter = SimpleAdapter(this, dataList, R.layout.produit_item, from, to)
                listView.adapter = adapter

                // Gestion du clic sur un item de la liste
                listView.setOnItemClickListener { _, _, position, _ ->
                    val selected = dataList[position]
                    Toast.makeText(
                        this,
                        "Nom: ${selected["nom"]}, Prix: ${selected["prix"]}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            { error ->
                Log.e("MainActivity", "Erreur requête: ${error.message}")
            }
        )
        queue.add(stringRequest)
    }
}