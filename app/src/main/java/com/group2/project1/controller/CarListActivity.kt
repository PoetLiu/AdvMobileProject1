package com.group2.project1.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.group2.project1.model.Car
import com.group2.project1.R

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
        rView?.layoutManager = LinearLayoutManager(this)
        rView?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }
}