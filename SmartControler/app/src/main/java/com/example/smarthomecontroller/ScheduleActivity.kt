package com.example.smarthomecontroller.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthomecontroller.R

class ScheduleActivity : AppCompatActivity() {

    private lateinit var timePicker: TimePicker
    private lateinit var setScheduleButton: Button
    private lateinit var clearScheduleButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        timePicker = findViewById(R.id.timePicker)
        setScheduleButton = findViewById(R.id.setScheduleButton)
        clearScheduleButton = findViewById(R.id.clearScheduleButton)

        setScheduleButton.setOnClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            // Example: Save the schedule to preferences or database
            showToast("Schedule set for $hour:$minute")
        }

        clearScheduleButton.setOnClickListener {
            // Example: Clear the saved schedule
            showToast("Schedule cleared")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
