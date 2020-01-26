package com.apoorva.echomark

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.skyfishjy.library.RippleBackground
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import io.chirp.chirpsdk.ChirpSDK
import io.chirp.chirpsdk.models.ChirpError
import io.chirp.chirpsdk.models.ChirpErrorCode
import org.json.JSONObject
import android.content.SharedPreferences
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout.HORIZONTAL
import kotlinx.android.synthetic.main.activity_transmission.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


private const val CHIRP_APP_KEY = "Fcae7f3Fc0611f27EDCdAa1Ef"
private const val CHIRP_APP_SECRET = "daB2BAA1Db87D1113cA74BAA6AA03B2FB6b7FF12Fd89ab60c3"
private const val CHIRP_APP_CONFIG =
    "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"
private const val REQUEST_RECORD_AUDIO = 1

class Transmission : AppCompatActivity() {
    private lateinit var chirpSdk: ChirpSDK
    private lateinit var context: Context
    private lateinit var configError: ChirpError
    private lateinit var identifier: String
    private lateinit var payload: ByteArray
    val attendees: ArrayList<String> = ArrayList()
    val attendeeObjects : ArrayList<AttendanceResponses> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transmission)
        val rippleBackground = findViewById<View>(R.id.content) as RippleBackground
        val sendBtn = findViewById<Button>(R.id.sendBtn) as Button
        val refreshBtn = findViewById<Button>(R.id.refresh) as Button

        addAttendees()

        chirpSdk = ChirpSDK(this, CHIRP_APP_KEY, CHIRP_APP_SECRET)
        configError = chirpSdk.setConfig(CHIRP_APP_CONFIG)
        identifier = "hello"
        payload = identifier.toByteArray()

        var error = chirpSdk.setConfig(CHIRP_APP_CONFIG)
        if (error.code == 0) {
            Log.v("ChirpSDK: ", "Configured ChirpSDK")
        } else {
            Log.e("ChirpError: ", error.message)
        }

        chirpSdk.onReceived { data: ByteArray?, channel: Int ->
            /**
             * onReceived is called when a receive event has completed.
             * If the payload was decoded successfully, it is passed in data.
             * Otherwise, data is null.
             */
            Log.i("Receiveeeeeeeee: ", "Success")
            if (data == null) {
                Log.i("Error: ", "No message body")
            } else {
                val message = String(data, Charsets.UTF_8)
                Log.i("Message: ", message)
                toast(message)
                val splitStrings = message.split("#")
                val path = "attendances.json"
                val params = JSONObject()
                val sharedPref = this?.getSharedPreferences("User", Context.MODE_PRIVATE)

                val a = sharedPref.getInt("id", 1000)
                params.put("user_id", a.toString())
                params.put("session_id", splitStrings[0])
                params.put("key", splitStrings[1])
                val service = ServiceVolley()
                val apiController = APIController(service)

                apiController.post(path, params) { response ->
                    // Parse the result
                    Log.i("Success", response.toString())
                }

            }
        }

        chirpSdk.onSent { data: ByteArray, channel: Int ->
            Log.v("ChirpSDK", "Sent data")
            rippleBackground.stopRippleAnimation()
        }


        sendBtn?.setOnClickListener()
        {
            // Toast.makeText(this@MainActivity, "But
            // ton click" , Toast.LENGTH_LONG).show()
            var sessionKey = getSessionKey()
            sendPayload(sessionKey)
            //chirpSdk.send(payload)
            rippleBackground.startRippleAnimation()

//            intent = Intent(this, Transmission::class.java)
//            startActivity(intent)

        }

        refreshBtn?.setOnClickListener {
            addAttendees()
            Log.i("sizeee", attendees.size.toString())
            attendance_list.layoutManager = LinearLayoutManager(this)
            attendance_list.addItemDecoration(
                DividerItemDecoration(
                    attendance_list.getContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            attendance_list.adapter = AttendeeAdapter(attendeeObjects, this)
        }


    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                REQUEST_RECORD_AUDIO
            )
        } else {
            // Start ChirpSDK sender and receiver, if no arguments are passed both sender and receiver are started
            var error = chirpSdk.start(send = true, receive = true)
            if (error.code > 0) {
                Log.e("ChirpError: ", error.message)
            } else {
                Log.v("ChirpSDK: ", "Started ChirpSDK")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_RECORD_AUDIO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    var error = chirpSdk.start()
                    if (error.code > 0) {
                        Log.e("ChirpError: ", error.message)
                    } else {
                        Log.v("ChirpSDK: ", "Started ChirpSDK")
                    }
                }
                return
            }
        }
    }

    override fun onPause() {
        super.onPause()
        chirpSdk.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        chirpSdk.stop()
        try {
            chirpSdk.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getSessionKey(): String {
        val service = ServiceVolley()
        val apiController = APIController(service)
        var sessionKey: String = "Not Received"
        var sessionID: String = ""
        val path = "sessions.json"
        val params = JSONObject()
        val sharedPref = this?.getSharedPreferences("User", Context.MODE_PRIVATE)

        val a = sharedPref.getInt("id", 1000)
        Log.i("resss", a.toString())
        params.put("user_id", a.toString())

        apiController.post(path, params) { response ->
            // Parse the result
            Log.i("Success", response.toString())
            if (response != null) {
                sessionKey = response.getString("key")
                sessionID = response.getString("id")
            }

        }
        return sessionID + "#" + sessionKey
    }


    private fun sendPayload(payload: String) {
        /**
         * A payload is a byte array dynamic size with a maximum size defined by the config string.
         *
         * Convert String payload to  a byte array, and send it.
         */
        val payload = payload.toByteArray(Charsets.UTF_8)
        val maxPayloadLength = chirpSdk.maxPayloadLength()
        val payloadSize = payload.size
        if (payload.size > maxPayloadLength) {
            Log.e("ChirpSDKError: ", "Payload too long $payloadSize $maxPayloadLength")
            return;
        }
        val error = chirpSdk.send(payload)
        if (error.code > 0) {
            val volumeError = ChirpError(
                ChirpErrorCode.CHIRP_SDK_INVALID_VOLUME,
                "Volume too low. Please increase volume!"
            )
            if (error.code == volumeError.code) {
                context.toast(volumeError.message)
            }
            Log.e("ChirpSDKError: ", error.message)
        }
    }

    private fun addAttendees() {
//        attendees.add("AAA")
//        attendees.add("BBB")
        val service = ServiceVolley()
        val apiController = APIController(service)

//        val path = "attendances.json"
//        val params = JSONObject()
//        apiController.get(path, params) { response ->
//            // Parse the result
//            Log.i("Success", response.toString())
//            if(response!=null){
//                var arr = response.getJSONArray("")
//                for (i in 0 until arr.length()) {
//                    Log.i("fsdfd", arr.get(i).toString())
//                   // val item = response.getJSONObject(response(i))
//                }
//            }
//
//        }
        //attendees.clear()

        val queue = Volley.newRequestQueue(this)
        var url = "https://echomark.herokuapp.com/users/sign_in?username=gj&password=gj"
        url = "https://echomark.herokuapp.com/attendances.json"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.i("SUCCESS", "")
                //attendeeObjects.clear()
                val gson = Gson()
                val attendeeTutorialType = object : TypeToken<Array<AttendanceResponses>>() {}.type
                var attendee: Array<AttendanceResponses> =
                    gson.fromJson(response, attendeeTutorialType)
                for (item in attendee) {
                    Log.i("EEEE", item.username)
                    attendees.add(item.username)
                    attendeeObjects.add(item)
                }
                //Log.i("Ddassda", tutorials.toString() )
            },
            Response.ErrorListener { error ->
                Log.i(error.toString(), "FAILLL")
            })

        queue.add(stringRequest)


    }


}
