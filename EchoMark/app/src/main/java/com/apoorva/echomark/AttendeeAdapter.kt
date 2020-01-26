package com.apoorva.echomark


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_transmission.view.*
import kotlinx.android.synthetic.main.attendance_row.view.*

class AttendeeAdapter(val items : ArrayList<AttendanceResponses>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0?.tvAttendee?.text = items.get(p1).username
        p0?.delBtn.setOnClickListener {
            val queue = Volley.newRequestQueue(context)
            Log.i("RRRR", items.get(p1).username)
            val url = items.get(p1).url

            val stringRequest = StringRequest(
                Request.Method.DELETE, url,
                Response.Listener<String> { response ->
                    Log.i("SUCCESS", "DELETE")

                    //Log.i("Ddassda", tutorials.toString() )
                },
                Response.ErrorListener { error ->
                    Log.i(error.toString(),"FAILLL DELETE")
                })

            queue.add(stringRequest)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.attendance_row, parent, false))
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvAttendee = view.tv_attendee
    val delBtn = view.delBtn

}