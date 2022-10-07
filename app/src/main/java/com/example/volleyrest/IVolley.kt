package com.example.volleyrest

import org.json.JSONArray

interface IVolley {


    fun onResponse(response:String)
    fun onResponseGet(response:JSONArray)
}