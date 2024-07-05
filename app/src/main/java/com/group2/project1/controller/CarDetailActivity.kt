package com.group2.project1.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.group2.project1.R
import com.group2.project1.model.Car

class CarDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)

        val carImage : ImageView = findViewById(R.id.carImageView)
        val carTitleText: TextView = findViewById(R.id.carTitleTextView)
        val carMileageText: TextView = findViewById(R.id.carMileageTextView)
        val carHighlightsText: TextView = findViewById(R.id.carHighlightsTextView)
        val carPriceText: TextView = findViewById(R.id.carPriceTextView)
        val carColorText: TextView = findViewById(R.id.carColorsTextView)
        val carEngineText: TextView = findViewById(R.id.carEngineTextView)
        val carFuelText: TextView = findViewById(R.id.carFuelTextView)
        val carDetailsText: TextView = findViewById(R.id.carDetailTextView)

        val imageUrl = intent.getStringExtra("imageUrl") ?: ""
        // loading image from firebase storage.
        val storageRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(
            imageUrl
        )
        Glide.with(carImage.context).load(storageRef).into(carImage)

        val car = Car(intent)
        carTitleText.text = car.title
        carMileageText.text = car.mileage
        carHighlightsText.text = car.highlights
        carPriceText.text = car.getPriceCurrency()
        carColorText.text = car.color
        carEngineText.text = car.engine
        carFuelText.text = car.fuel
        carDetailsText.text = car.details

        val buyNowBtn: Button = findViewById(R.id.buyBtn)
        buyNowBtn.setOnClickListener {
            val myIntent: Intent = Intent(this, CheckoutActivity::class.java)
            car.putIntoIntent(myIntent)
            startActivity(myIntent)
        }
    }
}