package com.example.madpractical7

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

          dbHelper = DatabaseHelper(this)

        val etName: EditText = findViewById(R.id.etName)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPhone: EditText = findViewById(R.id.etPhone)
        val etAddress: EditText = findViewById(R.id.etAddress)

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val address = etAddress.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.insertPerson(name, email, phone, address) != -1L) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                etName.text.clear()
                etEmail.text.clear()
                etPhone.text.clear()
                etAddress.text.clear()
            }
        }

    }
}
