package com.example.volleyrest

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MyVolleyRequest {

    private var nRequestQueue:RequestQueue?=null
    private var context: Context?=null
    private var iVolley:IVolley?=null
    var imageLoader: ImageLoader?=null
    get

    val requestQueue:RequestQueue
    get(){
        if(nRequestQueue == null){
                nRequestQueue = Volley.newRequestQueue(context!!.applicationContext)
            }
            return nRequestQueue!!
    }

    private constructor(context:Context,iVolley:IVolley){
        this.context = context
        this.iVolley = iVolley
        nRequestQueue = requestQueue
        this.imageLoader = ImageLoader(nRequestQueue,object:ImageLoader.ImageCache{
            private val mCache = LruCache<String,Bitmap>(10)
            override fun getBitmap(url: String?): Bitmap? {
                TODO("Not yet implemented")
                return mCache.get(url)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                TODO("Not yet implemented")
                mCache.put(url, bitmap)
            }

        })
    }

    private constructor(context:Context){
        this.context = context
        nRequestQueue = requestQueue
        this.imageLoader = ImageLoader(nRequestQueue,object:ImageLoader.ImageCache{
            private val mCache = LruCache<String,Bitmap>(10)
            override fun getBitmap(url: String?): Bitmap? {
                TODO("Not yet implemented")
                return mCache.get(url)
            }

            override fun putBitmap(url: String?, bitmap: Bitmap?) {
                TODO("Not yet implemented")
                mCache.put(url, bitmap)
            }

        })
    }

    fun<T> addToRequestQueue(req: Request<T>){
        requestQueue.add(req);

    }

    //GET METHOD
    fun getRequest(url:String){
        val getRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener{
            response -> iVolley!!.onResponse(response.toString())
        }, Response.ErrorListener { error -> iVolley!!.onResponse(error.message!!) })
        addToRequestQueue(getRequest)
    }

    fun postRequest(url:String){
        val postRequest = object:StringRequest(Request.Method.POST, url,
        Response.Listener {
            response -> iVolley!!.onResponse(response.toString())

        }, Response.ErrorListener { error -> iVolley!!.onResponse(error.message!!)})
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["name"] = "Eddy Lee"
                params["value"] = "This is value posted from Android App"
                return super.getParams()
            }
        }
                addToRequestQueue(postRequest)
    }


    fun putRequest(url:String){
        val postRequest = object:StringRequest(Request.Method.PUT, url,
            Response.Listener {
                    response -> iVolley!!.onResponse(response.toString())

            }, Response.ErrorListener { error -> iVolley!!.onResponse(error.message!!)})
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["name"] = "Eddy Lee"
                params["value"] = "This is value put from Android App"
                return super.getParams()
            }
        }
        addToRequestQueue(postRequest)
    }


    fun patchRequest(url:String){
        val postRequest = object:StringRequest(Request.Method.PATCH, url,
            Response.Listener {
                    response -> iVolley!!.onResponse(response.toString())

            }, Response.ErrorListener { error -> iVolley!!.onResponse(error.message!!)})
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["name"] = "Eddy Lee"
                params["value"] = "This is value patched from Android App"
                return super.getParams()
            }
        }
        addToRequestQueue(postRequest)
    }

    fun deleteRequest(url:String){
       val deleteRequest = StringRequest(Request.Method.DELETE, url, Response.Listener{
           response -> iVolley!!.onResponse(response)
       }, Response.ErrorListener { error -> iVolley!!.onResponse(error.message!!) })
        addToRequestQueue(deleteRequest)
    }

    companion object{
        private var mInstance : MyVolleyRequest?=null
        @Synchronized
        fun getInstance(context: Context):MyVolleyRequest{
            if(mInstance == null){
                mInstance = MyVolleyRequest(context)
            }
            return mInstance!!
        }

        @Synchronized
        fun getInstance(context: Context, iVolley : IVolley):MyVolleyRequest{
            if(mInstance == null){
                mInstance = MyVolleyRequest(context, iVolley)
            }
            return mInstance!!
        }
    }



}