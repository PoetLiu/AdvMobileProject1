package com.group2.project1
import android.content.Intent
import java.text.NumberFormat
import java.util.Locale

data class Car(var title: String = "", var mileage: String = "", var highlights: String = "",
               var price: Double = 0.0, var color: String = "", var engine: String = "",
               var fuel: String = "", var details: String = "", var imageUrl: String = "") {

    fun getPriceCurrency(): String {
        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en", "CA"))
        format.maximumFractionDigits = 0
        return format.format(price)
    }

    fun putIntoIntent(intent: Intent) {
        intent.putExtra("title", title)
        intent.putExtra("mileage", mileage)
        intent.putExtra("highlights", highlights)
        intent.putExtra("price", getPriceCurrency())
        intent.putExtra("color", color)
        intent.putExtra("engine", engine)
        intent.putExtra("fuel", fuel)
        intent.putExtra("details", details)
        intent.putExtra("imageUrl", imageUrl)
    }
}
