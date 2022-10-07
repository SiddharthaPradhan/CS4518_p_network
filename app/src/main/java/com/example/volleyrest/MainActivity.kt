package com.example.volleyrest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity(), IVolley {
    private lateinit var getListView: ListView
    private var dataList: ArrayList<Array<String>> = ArrayList<Array<String>>()
    private lateinit var adapter:MyArrayAdapter

    override fun onResponse(response:String){
        Log.i("PEEPEEPOOPOO", response)

        Toast.makeText(this@MainActivity, ""+response,Toast.LENGTH_SHORT).show()
    }

    override fun onResponseGet(response: JSONArray) {
        adapter.removeAll()
        for (i in 0 until response.length()){
            val singleItem: Array<String> = arrayOf("","","")
            singleItem[0] = (response.get(i) as JSONObject).get("id").toString()
            singleItem[1] = (response.get(i) as JSONObject).get("name").toString()
            try{
                singleItem[2] = (response.get(i) as JSONObject).get("url").toString()
            } catch(e: Exception){
                singleItem[2] = ""
            }
            Log.i("PEEPEEPOOPOO", singleItem.toString())
            adapter.addItem(singleItem)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getBtn = findViewById<Button>(R.id.get)
        val postBtn = findViewById<Button>(R.id.post)
        val putBtn = findViewById<Button>(R.id.put)
        val patchBtn = findViewById<Button>(R.id.patch)
        val deleteBtn = findViewById<Button>(R.id.delete)

        // new stuff
        refreshDataList()
        getListView = findViewById(R.id.getList)
        adapter = MyArrayAdapter(this,dataList)
        getListView.adapter = adapter

        Log.i("PEEPEEPOOPOOARRAYLIST", dataList.size.toString())

        getBtn.setOnClickListener {
            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).getRequest("http://10.0.2.2:3000/people")
        }

        postBtn.setOnClickListener {
            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).postRequest("https://jsonplaceholder.typicode.com/posts")
        }

        putBtn.setOnClickListener {
            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).putRequest("https://jsonplaceholder.typicode.com/posts/1")
        }

        // Do we need a patch button?
        patchBtn.setOnClickListener {
            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).patchRequest("https://jsonplaceholder.typicode.com/posts/1")
        }


        deleteBtn.setOnClickListener {
            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).deleteRequest("https://jsonplaceholder.typicode.com/posts/1")
        }










    }

    private fun refreshDataList(){
        // perform get request for all the data and populate dataList
        MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).getRequest("http://10.0.2.2:3000/people")

    }
}