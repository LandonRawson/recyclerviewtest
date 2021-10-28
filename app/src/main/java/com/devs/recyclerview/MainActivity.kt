package com.devs.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter

    private var titleList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val unParsedData = getData()
        titleList = unParsedData[0]
        descriptionList = unParsedData[1]
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerAdapter = RecyclerAdapter(titleList,descriptionList)
        recyclerView.adapter = recyclerAdapter

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun getData(): Array<MutableList<String>> {
        var routedata = getDataFromDb()
        var cords = mutableListOf<String>()
        var title = mutableListOf<String>()
        for (aList in routedata) {
            cords.add("Coordinates: " + aList.elementAt(0) + "," + aList.elementAt(1))
            title.add("" + aList.elementAt(2))
        }
        return arrayOf(title, cords)

    }

    private fun getDataFromDb(): List<List<String>>{
        return listOf(listOf("30","-90","Home"),listOf("29","-90","Work"),listOf("29","-89","Louisiana Tech"),listOf("29","-89.5","Tractor Supply"))
    }

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),0){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            var startPosition = viewHolder.bindingAdapterPosition
            var endPosition = target.bindingAdapterPosition
            Collections.swap(titleList, startPosition, endPosition)
            recyclerView.adapter?.notifyItemMoved(startPosition, endPosition)//send back to db here
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }

    }

}