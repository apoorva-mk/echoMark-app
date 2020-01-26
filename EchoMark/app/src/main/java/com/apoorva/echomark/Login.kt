package com.apoorva.echomark

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.content.SharedPreferences
import android.R.id.edit
import org.json.JSONObject


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var name : EditText = findViewById(R.id.name)
        var password: EditText = findViewById(R.id.password)
        var loginBtn : Button = findViewById(R.id.loginBtn)

        loginBtn?.setOnClickListener {
            var inname = name.text.toString()
            var pass = password.text.toString()
            Authenticate(inname, pass)

        }
    }

    private fun Authenticate(name: String, pass: String){
        val queue = Volley.newRequestQueue(this)
        var url = "https://echomark.herokuapp.com/users/sign_in?username=gj&password=gj"
        url = "https://echomark.herokuapp.com/users/sign_in?username=".plus(name).plus("&password=").plus(pass)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("SUCCESS", "DUMBICCH")
                var obj = JSONObject(response)
                val sharedPreferences = getSharedPreferences(
                    "User",
                    Context.MODE_PRIVATE
                )
                val myEdit = sharedPreferences.edit()
                myEdit.putString("username", obj.getString("username"))
                myEdit.putInt("id", obj.getInt("id"))
                myEdit.putInt("role", obj.getInt("role"))
                myEdit.commit()
                Log.i("iii", sharedPreferences.getInt("id", 1000).toString())
                Log.i("resss: ", obj.getString("username"))
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            },
            Response.ErrorListener { error ->
                Log.i(error.toString(),"FAILLL")
            })

        queue.add(stringRequest)
    }
}
