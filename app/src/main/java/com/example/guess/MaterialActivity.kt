package com.example.guess

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guess.databinding.ActivityMaterialBinding
import com.example.guess.databinding.ContentMaterialBinding

const val REQUEST_CODE: Int = 100

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
                    if (message == getString(R.string.you_got_it)) {
                        val intent = Intent(this, RecordActivity::class.java)
                        intent.putExtra("count", viewModel.count)
                        intent.putExtra("answer", viewModel.secret)
//                        startActivity(intent)
                        startActivityForResult(intent, REQUEST_CODE)
                    }

                }
                .show()

        })

        binding.fab.setOnClickListener {
            replay()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                /**do something*/
                replay()
            }else if(resultCode == 222){
                Toast.makeText(this, data?.getStringExtra("message"), Toast.LENGTH_SHORT).show()
            }
//        }
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