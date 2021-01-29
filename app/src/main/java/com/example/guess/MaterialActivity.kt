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
    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaterialBinding.inflate(layoutInflater)
        contentBinding = binding.contentMaterial
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)
        viewModel.counter.observe(this, Observer {
            contentBinding.counter.text = it.toString()
        })
        viewModel.result.observe(this, Observer {
            val message = when (it) {
                GameResult.BIGGER -> getString(R.string.bigger)
                GameResult.SMALLER -> getString(R.string.smaller)
                GameResult.RIGHT -> getString(R.string.you_got_it)
                GameResult.ENTER_NUMBER -> getString(R.string.enter_number)
            }
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    contentBinding.edNumber.text.clear()
                }
                .show()

        })

        binding.fab.setOnClickListener {
            replay()
        }
    }

    fun check(view: View) {
        val number = if (contentBinding.edNumber.text.isNotEmpty()) {
            contentBinding.edNumber.text.toString().toInt()
        } else {
            -111
        }
        viewModel.guess(number)
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

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle("Replay Game")
            .setMessage("Are you sure?")
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                viewModel.reset()
                contentBinding.edNumber.text.clear()
            }
            .setNeutralButton("Cancel", null)
            .show()
    }
}