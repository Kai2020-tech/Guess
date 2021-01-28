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
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(com.example.guess.R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "secret: ${secretNumber.secret}")
    }

    fun check(view: View) {
        val number = binding.edNumber.text.toString().toInt()
        Log.d(TAG, "number: $number")
        val diff = secretNumber.validate(number)
        var message = getString(R.string.you_got_it)    //resources.getString
        if (diff < 0) {
            message = getString(R.string.bigger)
        } else if (diff > 0) {
            message = getString(R.string.smaller)
        }
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok),null)
            .show()
    }
}