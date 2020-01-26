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
import kotlinx.android.synthetic.main.session_attended_row.view.*

class SessionAdapter(val items : ArrayList<Sessions>, val context: Context) : RecyclerView.Adapter<SessionViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SessionViewHolder {
        return SessionViewHolder(LayoutInflater.from(context).inflate(R.layout.session_attended_row, p0, false))
    }

    override fun onBindViewHolder(p0: SessionViewHolder, p1: Int) {
        if(items.get(p1).id!=null)
        p0?.sessionId.text = items.get(p1).id.toString()

        else{
            p0?.sessionId.text="-"
        }
        p0?.sessionDate.text = items.get(p1).created_at
    }


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

}

class SessionViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val sessionId = view.sessionId
    val sessionDate = view.sessionDate

}