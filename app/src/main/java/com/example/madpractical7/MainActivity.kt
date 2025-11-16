package com.example.madpractical7

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PersonAdapter
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        val personList = mutableListOf<Person>()
        adapter = PersonAdapter(personList) { position ->
            val person = personList[position]
            if (person.id.isNotEmpty() && person.id.all { it.isDigit() }) {
                if (dbHelper.deletePerson(person.id)) {
                    adapter.remove(position)
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
            } else {
                adapter.remove(position)
            }
        }
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.btn).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loadData()
    }

    private fun loadData() {
        val dbPersons = dbHelper.getAllPersons().toMutableList()
        adapter.update(dbPersons)
        
        fetchJsonData()
    }

    private fun fetchJsonData() {
        CoroutineScope(Dispatchers.IO).launch {
            val url = "https://api.json-generator.com/templates/qjeKFdjkXCdK/data"
            val token = "rbn0rerl1k0d3mcwgw7dva2xuwk780z1hxvyvrb1"
            val data = HttpRequest.makeServiceCall(url, token)
            val list = mutableListOf<Person>()
            
            if (data != null) {
                try {
                    val arr = JSONArray(data)
                    for (i in 0 until arr.length()) {
                        list.add(Person.fromJson(arr.getJSONObject(i)))
                    }
                } catch (_: Exception) {}
            }
            
            withContext(Dispatchers.Main) {
                val dbPersons = dbHelper.getAllPersons()
                val combined = (dbPersons + list).toMutableList()
                adapter.update(combined)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}
