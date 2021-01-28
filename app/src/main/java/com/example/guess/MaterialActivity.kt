package com.example.guess

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guess.databinding.ActivityMainBinding
import com.example.guess.databinding.ActivityMaterialBinding
import com.example.guess.databinding.ContentMaterialBinding

class MaterialActivity : AppCompatActivity() {
    private lateinit var viewModel: GuessViewModel
    lateinit var binding: ActivityMaterialBinding
    lateinit var contentBinding: ContentMaterialBinding
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialBinding.inflate(layoutInflater)
        contentBinding = binding.contentMaterial
        setContentView(binding.root)
        Log.d(TAG, "secret: ${secretNumber.secret}")
        setSupportActionBar(binding.toolbar)
        viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)
        viewModel.counter.observe(this, Observer {
            contentBinding.counter.text = it.toString()
        })



        binding.fab.setOnClickListener { view ->
            replay()
        }

    }

    fun check(view: View) {
        viewModel.guess(3)
//        val number = contentBinding.edNumber.text.toString().toInt()
//        contentBinding.counter.text = secretNumber.count.toString()
//        Log.d(TAG, "number: $number")
//        val diff = secretNumber.validate(number)
//        var message = getString(R.string.you_got_it)    //resources.getString
//        if (diff < 0) {
//            message = getString(R.string.bigger)
//        } else if (diff > 0) {
//            message = getString(R.string.smaller)
//        }
////        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//        val dialog = AlertDialog.Builder(this)
//            .setTitle(getString(R.string.dialog_title))
//            .setMessage(message)
//            .setPositiveButton(getString(R.string.ok)) { _, _ ->
//                contentBinding.edNumber.text.clear()
//            }
//            .show()
    }

    fun replay(){
        AlertDialog.Builder(this)
            .setTitle("Replay Game")
            .setMessage("Are you sure?")
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                secretNumber.reset()
                contentBinding.counter.text = secretNumber.count.toString()
                contentBinding.edNumber.text.clear()
            }
            .setNeutralButton("Cancel", null)
            .show()
    }
}