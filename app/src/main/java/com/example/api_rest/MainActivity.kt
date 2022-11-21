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
    lateinit var float_delete: FloatingActionButton
    lateinit var float_edit : FloatingActionButton
    var index : Int = -1
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
                    float_delete = findViewById(R.id.delete)
                    float_edit = findViewById(R.id.put)
                    float_edit.setOnClickListener {
                        println("button edit")
                    }
                    //create adapter
                    adapter = CustomAdapter(list)

                    //create layoutManager of recycler
                    val layoutRecycler = LinearLayoutManager(applicationContext)

                   /* adapter.setOnItemClickListener(object : CustomAdapter.onItemClickListener{


                        override fun onItemClick(position: Int) {
                            println("item selected")

                            float_edit.setOnClickListener {
                                //println("dkhalna lel methode float -delete ")
                                intent = Intent(applicationContext, MainActivity2::class.java)
                                intent.putExtra("id",list?.get(index).toString())

                                startActivity(intent)



                            }


                        }

                    })*/
                    adapter.setOnItemClickListener(object : CustomAdapter.onItemClickListener{


                        override fun onItemClick(position: Int) {

                            float_edit.setOnClickListener {

                                intent = Intent(applicationContext, MainActivity2::class.java)
                                intent.putExtra("id",list?.get(position)?.code.toString())
                                intent.putExtra("intitule",list?.get(position)?.intitulé.toString())
                                intent.putExtra("société",list?.get(position)?.société.toString())
                                intent.putExtra("specialité",list?.get(position)?.specialité.toString())
                                intent.putExtra("pays",list?.get(position)?.pays.toString())
                                intent.putExtra("nbpostes",list?.get(position)?.nbpostes.toString())


                                startActivity(intent)



                            }

                            float_delete.setOnClickListener {

                                val scope = CoroutineScope(Dispatchers.Main)
                                scope.launch {
                                    try {

                                        println("hellooo")
                                        ApiClient.apiService.deleteOffre(list?.get(position)?.code.toString())
                                       println(list)
                                        println(position)
                                        index = position
                                        list?.remove(list!!.get(index))
                                        adapter.notifyItemRemoved(position)
                                        adapter.notifyItemRangeChanged(position, list!!.size)
                                        adapter.notifyDataSetChanged()

                                    }
                                    catch (e: Exception) {
                                        Log.e("Error",e.message.toString())
                                    }
                                }

                            }


                        }

                    })
                    recycler.layoutManager=layoutRecycler
                    recycler.adapter = adapter

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