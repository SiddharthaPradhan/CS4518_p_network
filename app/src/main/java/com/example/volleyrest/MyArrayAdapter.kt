package com.example.volleyrest

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class MyArrayAdapter(private val context: Context,
                     private var dataSource: ArrayList<Array<String>>): BaseAdapter() {
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(p0: Int): Any {
        return dataSource[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    fun addItem(item: Array<String>){
        dataSource.add(item)
        notifyDataSetChanged()
    }
    fun removeAll(){
        dataSource = arrayListOf()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflator  = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflator.inflate(
            R.layout.list_view_item,
            p2,
            false
        )

        val idTextView = rowView.findViewById(R.id.idText) as TextView
        val nameTextView = rowView.findViewById(R.id.nameText) as TextView
        val imageView = rowView.findViewById(R.id.thumbnail) as ImageView
        val rowItem = getItem(p0) as Array<*>
        idTextView.text = rowItem[0] as String
        nameTextView.text = rowItem[1] as String
        if(rowItem[2] as String == ""){
            Picasso.get().load(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).resize(50, 50)
                .centerCrop().into(imageView)
        } else {
            Picasso.get().load(rowItem[2] as String).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).resize(50, 50)
                .centerCrop().into(imageView)
        }

        return rowView
    }
}