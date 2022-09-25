package com.example.coursereports

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar?.title = "Course Reports"


        var list: List<Class> = listOf(Class("CS 4518 Mobile & Ubiquitous Programming","A Term | 2022 | Firstname Lastname",true),Class("CS 4518 Mobile & Ubiquitous Programming","A Term | 2022 | Firstname Lastname",true))
        val liveList = MutableLiveData(list)
        var adapter: ListAdapter = ListAdapter(this,list)
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
                adapter = ListAdapter(this, it)
                recycler.adapter = adapter
            }
        }

        var classes: List<Class> = listOf(Class("CS 4518 Mobile & Ubiquitous Programming","A Term | 2022 | Firstname Lastname",true),Class("CS 4518 Mobile & Ubiquitous Programming","A Term | 2022 | Firstname Lastname",false),Class("OOP","A Term | 2022 | Firstname Lastname",false))
        val liveClasses = MutableLiveData(classes)
        var classRecycler: RecyclerView = findViewById(R.id.classes)
        var classAdapter: ListAdapter = ListAdapter(this,classes)
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
                classAdapter = ListAdapter(this, it)
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