package com.group2.project1.controller

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.group2.project1.R
import com.group2.project1.model.Car

class CarListActivity : AppCompatActivity() {
    private var rView: RecyclerView? = null
    private var adapter: CarAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_list)

        val query = FirebaseDatabase.getInstance().reference.child("cars")
        val options = FirebaseRecyclerOptions.Builder<Car>().setQuery(query, Car::class.java).build()
        adapter = CarAdapter(options)

        rView = findViewById(R.id.carsRV)
        rView?.layoutManager = getLayoutManager()
        rView?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        val orientation = resources.configuration.orientation
        // display two items per row for landscape mode.
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }
    }
}