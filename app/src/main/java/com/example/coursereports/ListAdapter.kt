package com.example.coursereports

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type

class ListAdapter(
    val activity: Activity,
    private val list: List<Course>,
    private val onClickListener: OnClickListener,
    private val filesDir: File,
    private val liveList: MutableLiveData<List<Course>>,
    private val classList: MutableLiveData<List<Course>>
) :
    RecyclerView.Adapter<com.example.coursereports.ListAdapter.ViewHolder>() {
    private val gson = Gson()
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
        val star: View = holder.itemView.findViewById(R.id.star)
        star.setOnClickListener {
            val file = File(filesDir, "favorites.json")
            val tempList = ArrayList<Course>(liveList.value)
            val idx = tempList.indexOf(list[position])
            val listCourse = classList.value?.find { it.id == list[position].id }
            if (idx != -1) {
                list[position].favorited = false
                listCourse?.favorited = false
                star.background =
                    ContextCompat.getDrawable(activity, R.drawable.ic_baseline_star_border_24)
                tempList.removeAt(idx)
            } else {
                list[position].favorited = true
                listCourse?.favorited = true
                star.background = ContextCompat.getDrawable(
                    activity,
                    R.drawable.ic_baseline_star_border_24_filled
                )
                tempList.add(list[position])
            }
            liveList.value = tempList
//            classList.value = ArrayList<Course>()
            file.writeText(gson.toJson(liveList.value))
            if (listCourse != null) {
                classList.value = ArrayList<Course>(classList.value)
            } else {
                classList.value = classList.value
            }
        }
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
            if (c.favorited) {
                star.background = ContextCompat.getDrawable(
                    activity,
                    R.drawable.ic_baseline_star_border_24_filled
                )
            } else {
                star.background = ContextCompat.getDrawable(
                    activity,
                    R.drawable.ic_baseline_star_border_24
                )
            }
        }

        init {
            name = itemView.findViewById(R.id.name)
            info = itemView.findViewById(R.id.info)
            star = itemView.findViewById(R.id.star)
        }
    }
}
