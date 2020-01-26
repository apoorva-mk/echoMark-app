package com.apoorva.echomark

import android.content.Context
import android.media.MediaCas
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_transmission.*
import kotlinx.android.synthetic.main.content_main.*

class PastClasses : AppCompatActivity() {

    var pastClasses : ArrayList<Sessions> = ArrayList<Sessions>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_classes)


        val sharedPref = this?.getSharedPreferences("User", Context.MODE_PRIVATE)
        val id = sharedPref.getInt("id", 1000)

        getSessions(id)
        attendance_list.layoutManager = LinearLayoutManager(this)
        attendance_list.addItemDecoration(
            DividerItemDecoration(
                attendance_list.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        attendance_list.adapter = SessionAdapter(pastClasses, this)
    }

    private fun getSessions(id: Int){
        val queue = Volley.newRequestQueue(this)
        var url = "https://echomark.herokuapp.com/users/sign_in?username=gj&password=gj"
        url = "https://echomark.herokuapp.com/sessions.json/"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("SUCCESS", "")
                pastClasses.clear()
                val gson = Gson()
                val sessionType = object : TypeToken<Array<Sessions>>() {}.type
                var sessionsLoaded: Array<Sessions> =
                    gson.fromJson(response, sessionType)
                for (item in sessionsLoaded) {
                    Log.i("EEEE", item.id.toString())
                    //sessionsAttended.add(item.username)
                    if(id ==item.user_id && item.id!=null){
                        pastClasses.add(item)
                    }

                }
                //Log.i("Ddassda", tutorials.toString() )
            },
            Response.ErrorListener { error ->
                Log.i(error.toString(), "FAILED SESSION LOADING")
            })

        queue.add(stringRequest)
    }
}
