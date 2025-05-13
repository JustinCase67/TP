package com.hugo.memo

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.pm.Signature
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.time.LocalDate

class AjouterActivity : AppCompatActivity() {

    private lateinit var ajouter:Button
    private lateinit var memo:EditText
    private lateinit var datebtn:Button
    private lateinit var dateText:TextView

    var dateChoisie : LocalDate = LocalDate.now().plusDays(1)
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
        datebtn = findViewById(R.id.dateBtn)
        dateText = findViewById(R.id.date)
        memo = findViewById(R.id.memoET)

        val ec = Ecouteur()

        ajouter.setOnClickListener(ec)
        datebtn.setOnClickListener(ec)

    }

    inner class Ecouteur : View.OnClickListener, OnDateSetListener {
        override fun onClick(v: View?) {

            if( v === ajouter){
                val texte:String = memo.text.toString()

                val memo = Memo(texte, dateChoisie)


                SingletonMemo.ajouterMemo(memo)
                finish()
            }
            else{
                val boiteDialog: DatePickerDialog = DatePickerDialog(this@AjouterActivity)
                boiteDialog.setOnDateSetListener(this)
                boiteDialog.show()
            }

        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            dateChoisie = LocalDate.of(year, month +1, dayOfMonth)
            dateText.text = dateChoisie.toString()
        }

    }

}