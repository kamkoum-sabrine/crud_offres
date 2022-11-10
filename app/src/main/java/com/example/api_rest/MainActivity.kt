package com.example.api_rest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.DELETE

class MainActivity : AppCompatActivity() {
    lateinit var recycler : RecyclerView
    private lateinit var adapter: CustomAdapter
    lateinit var add : FloatingActionButton
    lateinit var delete: Button
    lateinit var add_layout : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var list:MutableList<offre>?
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            try{
                val response = ApiClient.apiService.getOffres()
                if (response.isSuccessful && response.body() != null) {
                     list = response.body()
                    recycler = findViewById(R.id.recycleView)
                    //create adapter
                    adapter = CustomAdapter(list)

                    //create layoutManager of recycler
                    val layoutRecycler = LinearLayoutManager(applicationContext)

                    //add layoutManager to recycle
                    recycler.layoutManager=layoutRecycler
                    recycler.adapter = adapter


                   /* adapter.setOnItemClickListener(object : CustomAdapter.onItemClickListener{


                        override fun onItemClick(position: Int) {
                            var index = position
                            delete.setOnClickListener {
                              /*  list?.remove(list?.get(index))
                                adapter.notifyItemRemoved(position)
                                list?.let { it1 -> adapter.notifyItemRangeChanged(position, it1.size) }
                                adapter.notifyDataSetChanged()*/


                            }

                        }

                    })*/
                    Log.i("Success",response.body().toString())
                }else{
                    Log.e("Error",response.message())
                }

            } catch (e: Exception) {
                Log.e("Error",e.message.toString())
            }
        }
        add = findViewById(R.id.add)

        add.setOnClickListener {
            intent = Intent(applicationContext, MainActivity2::class.java)
            startActivity(intent)
        }

    }


}