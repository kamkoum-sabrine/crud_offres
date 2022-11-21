package com.example.api_rest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


internal class CustomAdapter(private var data: MutableList<offre>?): RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    internal  inner class MyViewHolder (view : View, listener: onItemClickListener) : RecyclerView.ViewHolder(view){
        var code : TextView = view.findViewById(R.id.code)
        var intitule : TextView = view.findViewById(R.id.intitule)
        var specialite : TextView = view.findViewById(R.id.specialite)
        var societe : TextView = view.findViewById(R.id.societe)
        var nbpostes : TextView = view.findViewById(R.id.nbpostes)
        var pays : TextView = view.findViewById(R.id.pays)
        var btn_delete : Button = view.findViewById(R.id.delete)
        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ligne,parent,false)
        return MyViewHolder(itemView,mListener)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = data?.get(position)
        if (item != null) {
            holder.code.setText("Code : "+item.code.toString())
            holder.intitule.setText("Intitule : "+item.intitulé)
            holder.pays.setText("Pays : "+item.pays)
            holder.societe.setText("Societe : "+item.société)
            holder.specialite.setText("Specialite : "+item.specialité)
            holder.nbpostes.setText("Nombre de postes : "+item.nbpostes.toString())
        }

        holder.btn_delete.setOnClickListener{

            var elem = this.getItemId(position);
            if (item != null) {
                println(item.code.toString())
            }
            val scope = CoroutineScope(Dispatchers.Main)
            scope.launch {
                try {

                    println("hellooo")
                     ApiClient.apiService.deleteOffre(item?.code.toString())


                        var list = ApiClient.apiService.getOffres()
                        println(list)
                        data = list.body()
                        println(data)
                        notifyItemRemoved(position)
                        data?.let { it1 -> notifyItemRangeChanged(position, it1.size) }
                        notifyDataSetChanged()
                        /* data?.remove( data?.get(position))
                    notifyItemRemoved(position)
                    data?.let { it1 -> notifyItemRangeChanged(position, it1.size) }
                    notifyDataSetChanged()*/




                }
                catch (e: Exception) {
                    Log.e("Error",e.message.toString())
                }
            }


        }


        /*holder.itemView.setOnClickListener{

            holder.name.text = ""
            holder.imageSrc.setImageResource(0)

            data
        }*/

        /* holder.float_button.setOnClickListener{
             this.getItemId(position)

         }*/

    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }








}