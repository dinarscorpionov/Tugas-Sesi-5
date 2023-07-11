package com.example.tugas_sesi_5

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var userListener : onItemClickListener

    private var listOfName = ArrayList<Users>()

    fun addedListOfUsers(list : List<Users>) {
        this.listOfName.clear()
        this.listOfName.addAll(list)
        notifyDataSetChanged()
    }

    interface onItemClickListener{
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        userListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return UserViewHolder(itemView, userListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = listOfName[position]
        Glide
            .with(holder.itemView.context)
            .load(currentItem.avatarUrl)
            .into(holder.imageFoto)
        holder.textUsername.text = currentItem.username
    }

    override fun getItemCount(): Int {
        return listOfName.size
    }

    class UserViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val imageFoto : ShapeableImageView = itemView.findViewById(R.id.imageFoto)
        val textUsername : TextView = itemView.findViewById(R.id.textListUsername)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}