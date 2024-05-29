package com.example.smarthomecontroller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class DashboardActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        requestQueue = Volley.newRequestQueue(this)

        val lightsButton: Button = findViewById(R.id.lightsButton)
        val thermostatButton: Button = findViewById(R.id.thermostatButton)
        val doorLockButton: Button = findViewById(R.id.doorLockButton)
        val settingsButton: Button = findViewById(R.id.settingsButton)

        lightsButton.setOnClickListener {
            startActivity(Intent(this, LightActivity::class.java))
        }

        thermostatButton.setOnClickListener {
            startActivity(Intent(this, ThermostatActivity::class.java))
        }

        doorLockButton.setOnClickListener {
            startActivity(Intent(this, DoorLockActivity::class.java))
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        fetchDashboardData()
    }

    private fun fetchDashboardData() {
        val url = "http://10.0.2.2:3000/dashboard"

        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "") ?: ""

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val message = response.getString("message")
                    showToast(message)
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast("Error: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                showToast("Failed to load dashboard data: ${error.message}")
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        requestQueue.add(jsonObjectRequest)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
