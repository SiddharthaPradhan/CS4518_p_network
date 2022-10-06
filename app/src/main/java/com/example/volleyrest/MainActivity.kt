package com.example.volleyrest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity(), IVolley {

    override fun onResponse(response:String){
        Toast.makeText(this@MainActivity, ""+response,Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getBtn = findViewById<Button>(R.id.get)
        val postBtn = findViewById<Button>(R.id.post)
        val putBtn = findViewById<Button>(R.id.put)
        val patchBtn = findViewById<Button>(R.id.patch)
        val deleteBtn = findViewById<Button>(R.id.delete)

        getBtn.setOnClickListener {
            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).getRequest("https://jsonplaceholder.typicode.com/todos/1")
        }

        postBtn.setOnClickListener {
            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).postRequest("https://jsonplaceholder.typicode.com/posts")
        }

        putBtn.setOnClickListener {
            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).putRequest("https://jsonplaceholder.typicode.com/posts/1")
        }

        patchBtn.setOnClickListener {
            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).patchRequest("https://jsonplaceholder.typicode.com/posts/1")
        }


        deleteBtn.setOnClickListener {
            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).deleteRequest("https://jsonplaceholder.typicode.com/posts/1")
        }










    }
}