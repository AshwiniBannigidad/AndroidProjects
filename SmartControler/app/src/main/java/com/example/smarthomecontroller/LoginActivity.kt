package com.example.smarthomecontroller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        requestQueue = Volley.newRequestQueue(this)

        val loginButton: Button = findViewById(R.id.loginButton)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                showToast("Please fill in all fields")
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        val url = "http://10.0.2.2:3000/login"
        val jsonRequest = JSONObject()
        jsonRequest.put("email", email)
        jsonRequest.put("password", password)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonRequest,
            Response.Listener { response ->
                try {
                    val token = response.getString("token")
                    saveToken(token)
                    showToast("Login successful!")
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast("Error: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                showToast("Login failed: ${error.message}")
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("token", token)
            apply()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
