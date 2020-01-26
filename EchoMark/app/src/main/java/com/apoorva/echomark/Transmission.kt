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
    private var mySessionKey : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Admin","Activity")
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
                Log.i("d11111111111", a.toString())
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
        }


        sendBtn?.setOnClickListener()
        {
            Log.i("HEREEEE:","iiiiiiiii")
            if(mySessionKey==""){

                var sessionKey = getSessionKey()
                mySessionKey = sessionKey
                Log.i("Keyyyyyyyy: ", sessionKey)
                sendBtn.setEnabled(false)
                sendBtn.setBackgroundColor(7)
            }

            else{
                Log.i("Keyyyyyyyyy:", "Reused")
                sendPayload(mySessionKey)
            }
            //chirpSdk.send(payload)
            rippleBackground.startRippleAnimation()
            Log.i(mySessionKey,"kiiiiiii")

        }

        refreshBtn?.setOnClickListener {
            //refreshBtn.setEnabled(false);
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

            //refreshBtn.setEnabled(true);

        }


    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    override fun onResume() {
        super.onResume()
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

    private fun getSessionKey() : String{
        val service = ServiceVolley()
        val apiController = APIController(service)
        var sessionKey: String = "Not Received"
        var sessionID: String = ""
        var key = ""
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
                Log.i("debug", sessionID + "#" + sessionKey)
                sendPayload(sessionID + "#" + sessionKey)
                key = (sessionID)
                sendBtn.setEnabled(true)
            }

        }
        return key
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
                attendeeObjects.clear()
                val gson = Gson()
                val attendeeTutorialType = object : TypeToken<Array<AttendanceResponses>>() {}.type
                var attendee: Array<AttendanceResponses> =
                    gson.fromJson(response, attendeeTutorialType)
                for (item in attendee) {
                    Log.i("EEEE", item.username)
                    attendees.add(item.username)
                    if(mySessionKey==item.session_id.toString()){
                        attendeeObjects.add(item)
                    }

                }
                //Log.i("Ddassda", tutorials.toString() )
            },
            Response.ErrorListener { error ->
                Log.i(error.toString(), "FAILLL")
            })

        queue.add(stringRequest)


    }


}
