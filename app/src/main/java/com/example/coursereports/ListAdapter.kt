package com.example.coursereports

import android.app.Activity
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type

class ListAdapter(val activity: Activity, private val list: List<Course>, private val onClickListener:OnClickListener):
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
        val star:View = holder.itemView.findViewById(R.id.star)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView
        private val info: TextView
        private val star: View


        fun bind(c: Course) {
            name.text = c.course_title
            info.text = c.term + " | " + c.first_name + " " + c.last_name + " | " + c.year
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
