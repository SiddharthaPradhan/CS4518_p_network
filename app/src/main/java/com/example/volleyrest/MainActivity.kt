package com.example.volleyrest

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity(), IVolley {
    private lateinit var getListView: ListView
    private lateinit var getEditText: EditText
    private var dataList: ArrayList<Array<String>> = ArrayList<Array<String>>()
    private lateinit var adapter:MyArrayAdapter
    private var isUserOnSearch: Boolean = true

    override fun onResponse(response:String){
        // TODO: remove toast after implementing everything
        Toast.makeText(this@MainActivity, ""+response,Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(response: VolleyError) {
        val msg = when (val returnCode: Int = response.networkResponse.statusCode){
            404 -> "GET request returned 404, ID could not be found"
            // Add more here
            else -> "Server Responded with Status Code: $returnCode"
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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
            adapter.addItem(singleItem)
        }
    }

    override fun onResponseGet(response: JSONObject) {
        adapter.removeAll()
        val singleItem: Array<String> = arrayOf("","","")
        singleItem[0] = response.get("id").toString()
        singleItem[1] = response.get("name").toString()
        try{
            singleItem[2] = response.get("url").toString()
        } catch(e: Exception){
            singleItem[2] = ""
        }
        adapter.addItem(singleItem)

    }

    override fun onResponse(response: JSONObject) {
        MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).getRequest("http://10.0.2.2:3000/people")
    }


//TODO https://picsum.photos/200 to get random images lmao

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getBtn = findViewById<Button>(R.id.get)
        val postBtn = findViewById<Button>(R.id.post)
//        val putBtn = findViewById<Button>(R.id.put)
//        val patchBtn = findViewById<Button>(R.id.patch)
//        val deleteBtn = findViewById<Button>(R.id.delete)

        // new stuff
        getEditText = findViewById(R.id.getRequestIdEditText)
        refreshDataList()
        getListView = findViewById(R.id.getList)
        adapter = MyArrayAdapter(this,dataList)
        getListView.adapter = adapter

        getListView.onItemLongClickListener = OnItemLongClickListener { parent, v, position, id ->
            val p = PopupMenu(this@MainActivity, v)
            val inflater = p.menuInflater
            inflater.inflate(R.menu.popup_menu, p.menu)
            p.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.deleteMenuButton -> {
                        MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).deleteRequest("http://10.0.2.2:3000/people",(adapter.getItem(position) as Array<*>)[0].toString().toInt())
                        adapter.removeItem(position)
                        true
                    }
                    R.id.editMenuButton -> {
                        alertInputDialog(false, (adapter.getItem(position) as Array<*>)[0].toString())
                        true
                    }
                    else -> {
                        true
                    }
                }

            }
            p.show()
            true
        }

        getBtn.setOnClickListener {
            if(isUserOnSearch){
                val getID = getEditText.text.toString()
                if (getID.isEmpty()){
                    Toast.makeText(this@MainActivity, "Enter an ID first", Toast.LENGTH_SHORT).show()
                } else {
                    isUserOnSearch = false
                    getBtn.text = "Reset"
                    MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).getRequest("http://10.0.2.2:3000/people", getID.toInt())

                }
            } else {
                isUserOnSearch = true
                MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).getRequest("http://10.0.2.2:3000/people")
                getBtn.text = "Search"
            }

        }

        postBtn.setOnClickListener {
            alertInputDialog(true, null)
            //MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).postRequest("https://jsonplaceholder.typicode.com/posts")
        }

//
//        // Do we need a patch button?
//        patchBtn.setOnClickListener {
//            MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).patchRequest("https://jsonplaceholder.typicode.com/posts/1")
//        }

    }

    private fun alertInputDialog(isForPost: Boolean, id : String?){
        val title: String = if (isForPost){
            "Add"
        } else {
            "Edit"
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        val inputID = EditText(this)
        inputID.inputType = InputType.TYPE_CLASS_NUMBER
        inputID.hint = "$title ID"
        if (!isForPost){
            inputID.setText(id)
            inputID.isEnabled = false
        }

        val inputName = EditText(this)
        inputName.inputType = InputType.TYPE_CLASS_TEXT
        inputName.hint = "$title Name"
        val parent = LinearLayout(this)
        parent.layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        parent.orientation = LinearLayout.VERTICAL
        parent.addView(inputID)
        parent.addView(inputName)
        builder.setView(parent)
        builder.setPositiveButton(title) { _: DialogInterface?, _: Int ->
            val nameText = inputName.text.toString()
            val idText = inputID.text.toString()
            if (nameText.isEmpty() || idText.isEmpty()){
                return@setPositiveButton
            }
            if(isForPost){
                MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).postRequest("http://10.0.2.2:3000/people", idText.toInt(), nameText, "https://picsum.photos/50")
            } else {
                MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).putRequest("http://10.0.2.2:3000/people", idText.toInt(), nameText, "https://picsum.photos/50")
            }
        }
        builder.setNegativeButton("Cancel",
            null)

        builder.show()
    }

    private fun refreshDataList(){
        // perform get request for all the data and populate dataList
        MyVolleyRequest.getInstance(this@MainActivity, this@MainActivity).getRequest("http://10.0.2.2:3000/people")

    }
}