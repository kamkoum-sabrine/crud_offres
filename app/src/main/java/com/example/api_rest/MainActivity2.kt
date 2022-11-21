package com.example.api_rest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {
    lateinit var intitule:EditText;
    lateinit var societe:EditText
    lateinit var specialite:EditText

    lateinit var pays:EditText
    lateinit var nbPostes:EditText
    lateinit var submit:Button
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        intitule = findViewById(R.id.edit_intitule)
        societe = findViewById(R.id.edit_societe)
        specialite = findViewById(R.id.edit_specialite)
        pays = findViewById(R.id.edit_pays)
        nbPostes = findViewById(R.id.edit_nbpostes)

        submit = findViewById(R.id.submit)





        submit.setOnClickListener {
            var newOffre: offre = offre()
            newOffre.specialité = specialite.text.toString()
            newOffre.société = societe.text.toString()
            newOffre.nbpostes = nbPostes.text.toString().toInt()
            newOffre.pays = pays.text.toString()
            newOffre.intitulé = intitule.text.toString()
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                try {
                    val response = ApiClient.apiService.createOffre(newOffre)
                    println(response)
                 //   Toast.makeText(applicationContext,"Offre created",Toast.LENGTH_SHORT).show()
                  /*  pays.setText("")
                    societe.setText("")
                    specialite.setText("")
                    nbPostes.setText("")
                    intitule.setText("")*/

                    intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)


                } catch (e: Exception) {
                    Log.e("Error", e.message.toString())
                }
            }


        }
    }
}