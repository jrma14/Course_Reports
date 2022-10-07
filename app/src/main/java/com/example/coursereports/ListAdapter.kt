package com.example.coursereports

import android.app.Activity
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(val activity: Activity, private val list: List<Class>,private val onClickListener:OnClickListener):
    RecyclerView.Adapter<com.example.coursereports.ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.classcard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView
        private val info: TextView
        private val star: View


        fun bind(c: Class) {
            name.text = c.name
            info.text = c.term + " | " + c.professor
            if (c.favorited){
                star.backgroundTintList = ColorStateList.valueOf(activity.resources.getColor(R.color.yellow))
            }
        }

        init {
            name = itemView.findViewById(R.id.name)
            info = itemView.findViewById(R.id.info)
            star = itemView.findViewById(R.id.star)
        }
    }
}
