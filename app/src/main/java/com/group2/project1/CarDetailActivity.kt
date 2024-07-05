package com.group2.project1

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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

        carTitleText.text = intent.getStringExtra("title")
        carMileageText.text = intent.getStringExtra("mileage")
        carHighlightsText.text = intent.getStringExtra("highlights")
        carPriceText.text = intent.getStringExtra("price")
        carColorText.text = intent.getStringExtra("color")
        carEngineText.text = intent.getStringExtra("engine")
        carFuelText.text = intent.getStringExtra("fuel")
        carDetailsText.text = intent.getStringExtra("details")
    }
}