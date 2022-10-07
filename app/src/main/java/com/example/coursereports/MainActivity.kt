package com.example.coursereports

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursereports.databinding.ActionBarBinding


class MainActivity : AppCompatActivity() {
    var classes: List<Class> = listOf(
        Class(
            "Mobile & Ubiquitous Programming","CS 4518",
            "A Term", "Firstname Lastname",
            "4.2","A","10",true
        ),
        Class(
            "Mobile & Ubiquitous Programming","CS 4518 ",
            "A Term", "Firstname Lastname",
            "4.2","A","10",true
        ),
        Class("OOP", "123","A Term", "prof", "1.2","B","100",false)
    )

    private val l = {
        course: Class ->
        val intent: Intent = Intent(this, classoverviewActivity::class.java)
        intent.putExtra("course_title", course.name)
        intent.putExtra("term",course.term)
        intent.putExtra("professor",course.professor)
        intent.putExtra("average",course.average)
        intent.putExtra("grade",course.grade)
        intent.putExtra("hours",course.hours)
        intent.putExtra("course_number",course.number)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        actionBar?.title = "Course Reports"

        this.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

        // Displaying the custom layout in the ActionBar
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val bind = ActionBarBinding.inflate(layoutInflater)
        bind.pageTitle.setText("Course Reports")
//        actionBar?.setCustomView(R.layout.actionbar)


        var list: List<Class> = listOf(
            Class(
                "Mobile & Ubiquitous Programming","CS 4518",
                "A Term", "Firstname Lastname",
                "4.2","A","10",true
            ),
            Class(
                "Mobile & Ubiquitous Programming","CS 4518 ",
                "A Term", "Firstname Lastname",
                "4.2","A","10",true
            )
        )
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
                adapter = ListAdapter(this, it, OnClickListener (l))
                recycler.adapter = adapter
            }
        }

        val liveClasses = MutableLiveData(classes)
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