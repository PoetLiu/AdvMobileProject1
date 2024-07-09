package com.group2.project1.controller

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.group2.project1.R
import com.group2.project1.model.Car

class CarAdapter(options: FirebaseRecyclerOptions<Car>) :
    FirebaseRecyclerAdapter<Car, CarAdapter.MyViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater  = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Car) {
        val storageRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(
            model.imageUrl
        )

        Glide.with(holder.carImage.context).load(storageRef).into(holder.carImage)
        holder.carTitleText.text = model.title
        holder.carMileageText.text = model.mileage
        holder.carHighlightsText.text = model.highlights
        holder.carPriceText.text = model.getPriceCurrency()

        val view = holder.itemView
        view.setOnClickListener {
            val myIntent: Intent = Intent(view.context, CarDetailActivity::class.java)
            model.putIntoIntent(myIntent)
            view.context.startActivity(myIntent)
        }
    }

    inner class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.activity_car_list_item, parent, false))
    {
        val carImage: ImageView = itemView.findViewById(R.id.carImageView)
        val carTitleText: TextView = itemView.findViewById(R.id.carTitleTextView)
        val carMileageText: TextView = itemView.findViewById(R.id.carMileageTextView)
        val carHighlightsText: TextView = itemView.findViewById(R.id.carHighlightsTextView)
        val carPriceText: TextView = itemView.findViewById(R.id.carPriceTextView)
    }
}