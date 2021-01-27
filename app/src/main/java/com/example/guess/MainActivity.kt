package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.guess.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(com.example.guess.R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MainActivity", "secret: ${secretNumber.secret}")
    }

    fun check(view: View) {
        val number = binding.edNumber.text.toString().toInt()
        Log.d("MainActivity", "number: $number")
        val diff = secretNumber.validate(number)
        var message = "You got it!"
        if (diff < 0) {
            message = "Bigger"
        } else if (diff > 0) {
            message = "Smaller"
        }
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val dialog = AlertDialog.Builder(this)
            .setTitle("Message")
            .setMessage(message)
            .setPositiveButton("OK",null)
            .show()
    }
}