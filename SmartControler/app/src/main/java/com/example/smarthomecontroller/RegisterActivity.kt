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

class RegistrationActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        requestQueue = Volley.newRequestQueue(this)

        val registerButton: Button = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val emailEditText: EditText = findViewById(R.id.emailEditText)
            val passwordEditText: EditText = findViewById(R.id.passwordEditText)
            val phoneEditText: EditText = findViewById(R.id.phoneEditText)

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val phone = phoneEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && phone.isNotEmpty()) {
                registerUser(email, password, phone)
            } else {
                showToast("Please fill in all fields")
            }
        }
    }

    private fun registerUser(email: String, password: String, phone: String) {
        val url = "http://10.0.2.2:3000/register"

        val requestBody = JSONObject()
        requestBody.put("email", email)
        requestBody.put("password", password)
        requestBody.put("phone", phone)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, requestBody,
            Response.Listener { response ->
                try {
                    val token = response.getString("token")
                    saveToken(token)
                    showToast("Registration successful!")
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast("Error: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                showToast("Failed to register: ${error.message}")
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
