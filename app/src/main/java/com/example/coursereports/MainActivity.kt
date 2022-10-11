package com.example.coursereports

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
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



        val file = File(filesDir, "favorites.json")
        val listType: Type = object : TypeToken<ArrayList<Course?>?>() {}.type
        var list: List<Course> = gson.fromJson(file.bufferedReader(), listType)


        val liveList = MutableLiveData(list)
        var adapter: ListAdapter = ListAdapter(this, list, OnClickListener(l))
        var recycler: RecyclerView = findViewById(R.id.favorites)
        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(
            recycler.context,
            layoutManager.orientation
        )
        recycler.addItemDecoration(dividerItemDecoration)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        liveList.observe(this) {
            it?.let {
                adapter = ListAdapter(this, it, OnClickListener(l))
                recycler.adapter = adapter
            }
        }

        val ps = RetrofitHelper.getInstance().create(PlanetscaleAPI::class.java)

        var classes: List<Course> = listOf()
        val liveClasses = MutableLiveData(classes)
        GlobalScope.launch {
            val res = ps.getAllCourses()
            Log.d("coursedata",res.body().toString())
            runOnUiThread {
                liveClasses.value = res.body()
            }

        }
        var classRecycler: RecyclerView = findViewById(R.id.classes)
        var classAdapter: ListAdapter = ListAdapter(this, classes, OnClickListener(l))
        val classLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        val classDividerItemDecoration = DividerItemDecoration(
            classRecycler.context,
            classLayoutManager.orientation
        )
        classRecycler.addItemDecoration(classDividerItemDecoration)
        classRecycler.layoutManager = classLayoutManager
        classRecycler.adapter = classAdapter
        liveClasses.observe(this) {
            it?.let {
                classAdapter = ListAdapter(this, it, OnClickListener(l))
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