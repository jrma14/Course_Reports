package com.example.coursereports

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    private val l = { course: Course ->
        val intent: Intent = Intent(this, classoverviewActivity::class.java)
        intent.putExtra("course_title", course.course_title)
        intent.putExtra("term", course.term)
        intent.putExtra("professor", course.first_name + " " + course.last_name)
        intent.putExtra("average", course.overallAverage)
        intent.putExtra("grade", course.expectedGrade)
        intent.putExtra("hours", course.outsideClassTime)
        intent.putExtra("course_number", course.course_number)
        intent.putExtra("survey_size", course.surveySize.toString())
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Displaying the custom layout in the ActionBar
        this.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val view: View = supportActionBar!!.customView
        val title: TextView = view.findViewById(R.id.page_title)
        title.text = "Course Reports"
        val gson: Gson = Gson()
        var term: String = ""

        val a: Button = findViewById(R.id.a)
        val b: Button = findViewById(R.id.b)
        val c: Button = findViewById(R.id.c)
        val d: Button = findViewById(R.id.d)
        var ac: Boolean = false
        var bc: Boolean = false
        var cc: Boolean = false
        var dc: Boolean = false

        a.setOnClickListener {
            if (ac) {
                a.background = getDrawable(R.drawable.pill)
                a.setTextColor(Color.parseColor("#aaaaaa"))
                term = ""
            } else {
                term = "A"
                a.background = getDrawable(R.drawable.pill_crimson)
                a.setTextColor(Color.WHITE)
                b.background = getDrawable(R.drawable.pill)
                b.setTextColor(Color.parseColor("#aaaaaa"))
                c.background = getDrawable(R.drawable.pill)
                c.setTextColor(Color.parseColor("#aaaaaa"))
                d.background = getDrawable(R.drawable.pill)
                d.setTextColor(Color.parseColor("#aaaaaa"))
                bc = false
                cc = false
                dc = false
            }
            ac = !ac
        }
        b.setOnClickListener {
            if (bc) {
                b.background = getDrawable(R.drawable.pill)
                b.setTextColor(Color.parseColor("#aaaaaa"))
                term = ""
            } else {
                term = "B"
                b.background = getDrawable(R.drawable.pill_crimson)
                b.setTextColor(Color.WHITE)
                d.background = getDrawable(R.drawable.pill)
                d.setTextColor(Color.parseColor("#aaaaaa"))
                c.background = getDrawable(R.drawable.pill)
                c.setTextColor(Color.parseColor("#aaaaaa"))
                a.background = getDrawable(R.drawable.pill)
                a.setTextColor(Color.parseColor("#aaaaaa"))
                ac = false
                cc = false
                dc = false
            }
            bc = !bc
        }
        c.setOnClickListener {
            if (cc) {
                c.background = getDrawable(R.drawable.pill)
                c.setTextColor(Color.parseColor("#aaaaaa"))
                term = ""
            } else {
                term = "C"
                c.background = getDrawable(R.drawable.pill_crimson)
                c.setTextColor(Color.WHITE)
                b.background = getDrawable(R.drawable.pill)
                b.setTextColor(Color.parseColor("#aaaaaa"))
                d.background = getDrawable(R.drawable.pill)
                d.setTextColor(Color.parseColor("#aaaaaa"))
                a.background = getDrawable(R.drawable.pill)
                a.setTextColor(Color.parseColor("#aaaaaa"))
                ac = false
                bc = false
                dc = false
            }
            cc = !cc
        }
        d.setOnClickListener {
            if (dc) {
                d.background = getDrawable(R.drawable.pill)
                d.setTextColor(Color.parseColor("#aaaaaa"))
                term = ""
            } else {
                term = "D"
                d.background = getDrawable(R.drawable.pill_crimson)
                d.setTextColor(Color.WHITE)
                b.background = getDrawable(R.drawable.pill)
                b.setTextColor(Color.parseColor("#aaaaaa"))
                c.background = getDrawable(R.drawable.pill)
                c.setTextColor(Color.parseColor("#aaaaaa"))
                a.background = getDrawable(R.drawable.pill)
                a.setTextColor(Color.parseColor("#aaaaaa"))
                ac = false
                cc = false
                bc = false
            }
            dc = !dc
        }

        val file = File(filesDir, "favorites.json")
        if (!file.exists()) {
            file.writeText("[]")
        }
        val listType: Type = object : TypeToken<ArrayList<Course?>?>() {}.type
        var list: List<Course> = gson.fromJson(file.bufferedReader(), listType)


        var allClasses: List<Course> = listOf()
        val liveClasses = MutableLiveData(allClasses)
        val currClasses = MutableLiveData(allClasses)

        val liveList = MutableLiveData(list)
        var adapter = ListAdapter(this, list, OnClickListener(l), filesDir, liveList, currClasses)
        var recycler: RecyclerView = findViewById(R.id.favorites)
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(
            recycler.context, layoutManager.orientation
        )
        recycler.addItemDecoration(dividerItemDecoration)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        liveList.observe(this) {
            it?.let {
                adapter = ListAdapter(this, it, OnClickListener(l), filesDir, liveList, currClasses)
                recycler.adapter = adapter
                val favoriteText: TextView = findViewById(R.id.textView)
                val favorites: RecyclerView = findViewById(R.id.favorites)
                if (it.isEmpty()) {
                    favoriteText.visibility = View.INVISIBLE
                    favorites.visibility = View.INVISIBLE
                } else {
                    favoriteText.visibility = View.VISIBLE
                    favorites.visibility = View.VISIBLE
                }
            }
        }

        val ps = RetrofitHelper.getInstance().create(PlanetscaleAPI::class.java)

        GlobalScope.launch {
            val res = ps.getAllCourses()
            for (course in liveList.value!!) {
                Log.d("coursedata", "Favoriting " + course.id + " " + course.course_title)
                val listCourse = res.body()?.find { it.id == course.id }
                if (listCourse != null) {
                    listCourse.favorited = true
                }
            }
            runOnUiThread {
                liveClasses.value = res.body()
                currClasses.value = res.body()
            }

        }

        val searchText: EditText = findViewById(R.id.searchText)
        var filter = searchText.text.toString()
        searchText.addTextChangedListener {
            filter = searchText.text.toString()
            currClasses.value = liveClasses.value?.filter { it: Course ->
                it.course_title.lowercase()
                    .contains(filter.lowercase()) || it.course_number.lowercase()
                    .contains(filter.lowercase()) || it.course_number.lowercase().replace(" ", "")
                    .contains(filter.lowercase()) && (it.term.isNotEmpty() || it.term == term)
            }
        }

        var classRecycler: RecyclerView = findViewById(R.id.classes)
        var classAdapter = ListAdapter(
            this, currClasses.value!!, OnClickListener(l), filesDir, liveList, currClasses
        )
        val classLayoutManager = LinearLayoutManager(this)
        val classDividerItemDecoration = DividerItemDecoration(
            classRecycler.context, classLayoutManager.orientation
        )
        classRecycler.addItemDecoration(classDividerItemDecoration)
        classRecycler.layoutManager = classLayoutManager
        classRecycler.adapter = classAdapter
        currClasses.observe(this) {
            it?.let {
                classAdapter =
                    ListAdapter(this, it, OnClickListener(l), filesDir, liveList, currClasses)
                classRecycler.adapter = classAdapter
            }
        }

//        https://stackoverflow.com/questions/24471109/recyclerview-onclick

//        setRecyclerViewItemTouchListener()
    }

//    private fun setRecyclerViewItemTouchListener() {
//
//        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
//                val position = viewHolder.bindingAdapterPosition
//                val currentHomework: Homework? =  dataSource.getHomeworkList().value?.get(position)
//                if (currentHomework != null){
//                    dataSource.removeHomework(currentHomework)
//                }
//            }
//        }
//
//        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
//        itemTouchHelper.attachToRecyclerView(mRecycler)
//    }
}