package com.apoorva.echomark

import android.Manifest
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.util.Log
import io.chirp.chirpsdk.ChirpSDK
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.*
import com.google.gson.Gson
import com.scwang.wave.MultiWaveHeader
import io.chirp.chirpsdk.models.ChirpError
import io.chirp.chirpsdk.models.ChirpErrorCode
import khttp.get
import khttp.post
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


private const val CHIRP_APP_KEY = "FBD7A2C6Be3CC67DBfeFeeBf3"
private const val CHIRP_APP_SECRET = "21F7fdfF3840332006e51D3a88abed7d1b6eB259DCe28eFdDC"
private const val CHIRP_APP_CONFIG = "qbMUMM7nHFxEra/jiziXK1qDXKtolXD8PxRdA36rR8zrm6TV9GsOrjJPM6shsY05NjuBQsr7pq3vKqoMYCmFHJ681DHJ8TPAsUTLE7G3F3dOOW8Yhaw84cD6FEuJX7O61zbH+K9M1WguOMOh/i6zl53FPqWTRpW6Z0aFkmMJ0hky9z2u/MWVfEUuuAJ2Wr+Xa8lRMCSitzqYlJE/MZJ8wvpLHNk1Qb0Hj3LCsUsKfjnFVmbK0yv7pRlTDeAddyy/SIEWCaUrXdTYhxkqguyf6oSLxoI4O+wLNUFookf7pBqD677h4I/vkZgYtrwxVPtWBBEsPopL/x5Q9CDvqp3I1GfGaXNR/0rsKc26q/L740b+SVRf+rk2YA3N0ByxvU55py2jP5U0iXWECEYs4xF+PaNB48qXxbzCbBvKd+GjaiWCmU8vv2kkpVt/07F2DgYavaZcjK6f4HUM92hzQHVGDuHYbjVCVxPNlEwzJdS/HGYZzf1Ioa6LeHHLfP+tXg/hrWLYjN5WGdLsqquuuFJwhg5bEX+lPNz4sd1GEnaHmX9c62/PObG4wM6M6ON/YYCYWzMa/zwgp7eJ7HjPdcD9d9CYXl8tpk37tPgRytL6rUKWB3pYFMsG8BSB9V+VzwCJ28dMj/Q/o4HfkJ1PzmtN6TxfUc1Pd6eNzCeSHolTxBK37HZ0SGYa4Y2X1BNM5JFGIWj6yu6vxx2Xi0mhj6h1pWVbYRv/z1p8bg56HDtZcsOcRxYANtJ5cXFsVpK9YxKDuZ621SycvR8BRpSYSm4txCQTpf6ElThyRR6f2olYv5afCtFdSyvUmJarzO/AviLVoZnjmJ3iYeGCJ6gNRaAobWj4qmRE7vR6LvvAr1cwZ+E="
private const val REQUEST_RECORD_AUDIO = 1



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var chirpSdk: ChirpSDK
    private lateinit var context: Context
    private lateinit var configError: ChirpError
    private lateinit var identifier : String
    private lateinit var payload: ByteArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        var waveHeader = findViewById(R.id.waveHeader) as MultiWaveHeader
        waveHeader.start()


        chirpSdk = ChirpSDK(this, CHIRP_APP_KEY, CHIRP_APP_SECRET)
        configError = chirpSdk.setConfig(CHIRP_APP_CONFIG)
        identifier = "hello"
        payload= identifier.toByteArray()

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
            if(data==null) {
                Log.i("Error: ", "No message body")
            }
            else{
                val message = String(data, Charsets.UTF_8)
                Log.i("Message: ", message)
            }
        }
        val button = findViewById(R.id.button) as Button
        button?.setOnClickListener()
        {
           // Toast.makeText(this@MainActivity, "Button click" , Toast.LENGTH_LONG).show()
            //chirpSdk.send(payload)
            intent = Intent(this, Transmission::class.java)
            startActivity(intent)

        }
        this.context = this
    }
    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
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




    private fun sendPayload(payload: String) {
        /**
         * A payload is a byte array dynamic size with a maximum size defined by the config string.
         *
         * Convert String payload to  a byte array, and send it.
         */
        val payload = payload.toByteArray(Charsets.UTF_8)
        val maxPayloadLength = chirpSdk.maxPayloadLength()
        if (payload.size > maxPayloadLength) {
            Log.e("ChirpSDKError: ", "Payload too long")
            return;
        }
        val error = chirpSdk.send(payload)
        if (error.code > 0) {
            val volumeError = ChirpError(ChirpErrorCode.CHIRP_SDK_INVALID_VOLUME, "Volume too low. Please increase volume!")
            if (error.code == volumeError.code) {
                context.toast(volumeError.message)
            }
            Log.e("ChirpSDKError: ", error.message)
        }
    }

}
