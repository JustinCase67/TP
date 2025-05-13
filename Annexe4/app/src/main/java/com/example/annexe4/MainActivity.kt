package com.example.annexe4

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileNotFoundException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var connaitre:Button
    lateinit var bonjour:TextView


    var util:Utilisateur? = null
    lateinit var lanceur: ActivityResultLauncher<Intent>;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        connaitre=findViewById(R.id.connaitreB)
        bonjour=findViewById(R.id.bonjourText)

        //POUR QUE CA FONCTIONNE QUAND ON SWITCH DE
//           if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){
//                util = savedInstanceState?.getSerializable("util",Utilisateur::class.java)
//            } else{
//                util = savedInstanceState?.getSerializable("util") as Utilisateur
//            }

        util = recupererSerialisation()

        if(util !=null)
            bonjour.text = "Bonjour ${util?.prenom} ${util?.nom} !"

        connaitre.setOnClickListener {
                v -> lanceur.launch(Intent(this@MainActivity,IdentificationActivity::class.java))
        }
        //Creation du lanceur:
        lanceur = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            CallBackUtilisateur()
        )

    }
    fun recupererSerialisation() : Utilisateur?{
        var temp:Utilisateur? =null
        try{
            val fis =openFileInput("info.ser")
            val ois = ObjectInputStream(fis)
            ois.use{
                temp = ois.readObject() as Utilisateur?

            }
        }catch (e: FileNotFoundException){
            Toast.makeText(this, "Fichier n'existe pas ", Toast.LENGTH_SHORT).show()

        }
        return temp
    }

    //override fun onSaveInstanceState(outState: Bundle) {
    //    super.onSaveInstanceState(outState)
    //   outState.putSerializable("util",util)
    //}

    inner class CallBackUtilisateur : ActivityResultCallback<ActivityResult> {
        override fun onActivityResult(result :ActivityResult)
        {
            //c'est le retour du boomerang
            if(result.resultCode == RESULT_OK)
            {
                val intentRetour = result.data
                if(intentRetour != null)
                {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){
                        util = intentRetour.getSerializableExtra("util",Utilisateur::class.java)
                    } else{
                        util = intentRetour.getSerializableExtra("util") as Utilisateur?
                    }
                    bonjour.text = "Bonjour ${util?.prenom} ${util?.nom} !"
                    serialiser()
                }
            }

        }
        fun serialiser(){
            val fos =openFileOutput("info.ser", MODE_PRIVATE)  //EN MODEPRIVATE ,ON VEUT QUE LES DERNIERES VERSIONS
            val oos = ObjectOutputStream(fos)
            oos.use{
                oos.writeObject(util)
            }
        }
    }
}