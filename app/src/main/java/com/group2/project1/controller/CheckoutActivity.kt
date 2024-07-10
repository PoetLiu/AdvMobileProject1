package com.group2.project1.controller

import com.group2.project1.R



import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent

class CheckoutActivity : AppCompatActivity() {

    private lateinit var tvCarTitle: TextView
    private lateinit var tvCarPrice: TextView
    private lateinit var tvCarColor: TextView
    private lateinit var tvCarEngine: TextView
    private lateinit var tvCarFuel: TextView
    private lateinit var etName: EditText
    private lateinit var etAddress: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etCreditCard: EditText
    private lateinit var etCreditCardExpiry: EditText
    private lateinit var etCreditCardCVV: EditText
    private lateinit var btnConfirm: Button
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        tvCarTitle = findViewById(R.id.tv_car_title)
        tvCarPrice = findViewById(R.id.tv_car_price)
        tvCarColor = findViewById(R.id.tv_car_color)
        tvCarEngine = findViewById(R.id.tv_car_engine)
        tvCarFuel = findViewById(R.id.tv_car_fuel)
        etName = findViewById(R.id.et_name)
        etAddress = findViewById(R.id.et_address)
        etPhone = findViewById(R.id.et_phone)
        etEmail = findViewById(R.id.et_email)
        etCreditCard = findViewById(R.id.et_credit_card)
        etCreditCardExpiry = findViewById(R.id.et_credit_card_expiry)
        etCreditCardCVV = findViewById(R.id.et_credit_card_cvv)
        btnConfirm = findViewById(R.id.btn_confirm)
        btnBack = findViewById(R.id.btn_back_checkout)

        val carTitle = intent.getStringExtra("title")
        val carPrice = intent.getStringExtra("price")
        val carColor = intent.getStringExtra("color")
        val carEngine = intent.getStringExtra("engine")
        val carFuel = intent.getStringExtra("fuel")

        tvCarTitle.text = carTitle
        tvCarPrice.text = carPrice
        tvCarColor.text = carColor
        tvCarEngine.text = carEngine
        tvCarFuel.text = carFuel

        btnConfirm.setOnClickListener {
            if (validateInput()) {
                displayOrderSummary()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun validateInput(): Boolean {
        val name = etName.text.toString().trim()
        val address = etAddress.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val creditCard = etCreditCard.text.toString().trim()
        val creditCardExpiry = etCreditCardExpiry.text.toString().trim()
        val creditCardCVV = etCreditCardCVV.text.toString().trim()

        if (name.isEmpty()) {
            etName.error = "Name is required"
            etName.requestFocus()
            return false
        }

        if (address.isEmpty()) {
            etAddress.error = "Address is required"
            etAddress.requestFocus()
            return false
        }

        if (phone.isEmpty()) {
            etPhone.error = "Phone number is required"
            etPhone.requestFocus()
            return false
        }

        if (!android.util.Patterns.PHONE.matcher(phone).matches()) {
            etPhone.error = "Invalid phone number"
            etPhone.requestFocus()
            return false
        }

        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Invalid email address"
            etEmail.requestFocus()
            return false
        }

        if (creditCard.isEmpty()) {
            etCreditCard.error = "Credit card number is required"
            etCreditCard.requestFocus()
            return false
        }

        if (creditCard.length != 16 || !creditCard.all { it.isDigit() }) {
            etCreditCard.error = "Invalid credit card number"
            etCreditCard.requestFocus()
            return false
        }

        if (creditCardExpiry.isEmpty()) {
            etCreditCardExpiry.error = "Expiry date is required"
            etCreditCardExpiry.requestFocus()
            return false
        }

        if (!creditCardExpiry.matches(Regex("(0[1-9]|1[0-2])/\\d{2}"))) {
            etCreditCardExpiry.error = "Invalid expiry date (MM/YY)"
            etCreditCardExpiry.requestFocus()
            return false
        }

        val parts = creditCardExpiry.split("/")
        val month = parts[0].toInt()
        val year = parts[1].toInt() + 2000

        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1

        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            etCreditCardExpiry.error = "Credit card has expired"
            etCreditCardExpiry.requestFocus()
            return false
        }

        if (creditCardCVV.isEmpty()) {
            etCreditCardCVV.error = "CVV is required"
            etCreditCardCVV.requestFocus()
            return false
        }

        if (creditCardCVV.length !in 3..4 || !creditCardCVV.all { it.isDigit() }) {
            etCreditCardCVV.error = "Invalid CVV"
            etCreditCardCVV.requestFocus()
            return false
        }

        return true
    }
    //Display order information
    private fun displayOrderSummary() {
        val summary = """
            Order Summary:
            Name: ${etName.text}
            Address: ${etAddress.text}
            Phone: ${etPhone.text}
            Email: ${etEmail.text}
            Credit Card: ${etCreditCard.text}
            Expiry Date: ${etCreditCardExpiry.text}
            CVV: ${etCreditCardCVV.text}
            Car Title: ${tvCarTitle.text}
            Car Price: ${tvCarPrice.text}
            Car Color: ${tvCarColor.text}
            Car Engine: ${tvCarEngine.text}
            Car Fuel: ${tvCarFuel.text}
        """.trimIndent()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Order Summary")
        builder.setMessage(summary)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(this, CarListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        })
        builder.show()
    }
}
