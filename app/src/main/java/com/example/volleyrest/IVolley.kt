package com.example.volleyrest

import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONObject

interface IVolley {


    fun onResponse(response:String)
    fun onResponse(response: VolleyError) // handle  errors
    fun onResponseGet(response:JSONArray) // handle multiple record get
    fun onResponseGet(response: JSONObject) // handler single record get
    fun onResponse(response: JSONObject) // handler single record put
}