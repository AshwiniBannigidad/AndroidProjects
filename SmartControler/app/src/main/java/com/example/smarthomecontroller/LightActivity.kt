package com.example.smarthomecontroller

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthomecontroller.R

class LightActivity : AppCompatActivity() {

    private var isLightOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light) // Set the XML layout

        val toggleLightsButton = findViewById<Button>(R.id.toggleLightsButton)

        toggleLightsButton.setOnClickListener {
            if (isLightOn) {
                turnLightsOff()
            } else {
                turnLightsOn()
            }
        }
    }

    private fun turnLightsOn() {
        // Example: Send command to turn lights on remotely
        showToast("Lights turned on")
        isLightOn = true
    }

    private fun turnLightsOff() {
        // Example: Send command to turn lights off remotely
        showToast("Lights turned off")
        isLightOn = false
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
