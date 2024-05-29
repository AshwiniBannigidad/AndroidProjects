package com.example.smarthomecontroller

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DoorLockActivity : AppCompatActivity() {

    private var isDoorLocked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_door_lock)

        val toggleLockButton: Button = findViewById(R.id.toggleLockButton)

        toggleLockButton.setOnClickListener {
            if (isDoorLocked) {
                unlockDoor()
            } else {
                lockDoor()
            }
        }
    }

    private fun lockDoor() {
        // Add logic to lock the door remotely
        showToast("Door Locked")
        isDoorLocked = true
    }

    private fun unlockDoor() {
        // Add logic to unlock the door remotely
        showToast("Door Unlocked")
        isDoorLocked = false
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
