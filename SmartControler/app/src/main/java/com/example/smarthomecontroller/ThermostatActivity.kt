package com.example.smarthomecontroller
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthomecontroller.R
import com.example.smarthomecontroller.activities.ScheduleActivity

class ThermostatActivity : AppCompatActivity() {

    private lateinit var currentTempTextView: TextView
    private lateinit var setPointTextView: TextView
    private var setPointTemperature: Int = 22 // default setpoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thermostat)

        currentTempTextView = findViewById(R.id.currentTempTextView)
        setPointTextView = findViewById(R.id.setPointTextView)
        val increaseTempButton = findViewById<Button>(R.id.increaseTempButton)
        val decreaseTempButton = findViewById<Button>(R.id.decreaseTempButton)
        val scheduleButton = findViewById<Button>(R.id.scheduleButton)


        // Display current temperature (simulated for this example)
        val currentTemp = 20 // Simulated current temperature
        currentTempTextView.text = "Current Temperature: $currentTemp°C"
        setPointTextView.text = "Set Point: $setPointTemperature°C"

        increaseTempButton.setOnClickListener {
            setPointTemperature++
            setPointTextView.text = "Set Point: $setPointTemperature°C"
            showToast("Set point increased to $setPointTemperature°C")
        }

        decreaseTempButton.setOnClickListener {
            setPointTemperature--
            setPointTextView.text = "Set Point: $setPointTemperature°C"
            showToast("Set point decreased to $setPointTemperature°C")
        }

        scheduleButton.setOnClickListener {
            // Open scheduling activity (implement separately)
            startActivity(Intent(this, ScheduleActivity::class.java))
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
