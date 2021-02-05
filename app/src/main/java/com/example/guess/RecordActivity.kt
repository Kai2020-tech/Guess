package com.example.guess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.guess.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity() {
    private lateinit var recordBinding: ActivityRecordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recordBinding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(recordBinding.root)
        val answer = intent.getIntExtra("answer", -1)
        val count = intent.getIntExtra("count", -1)
        recordBinding.tvAnswer.text = answer.toString()
        recordBinding.tvCount.text = count.toString()

        //save button
        recordBinding.btnSave.setOnClickListener {
            val nickname = recordBinding.edNickname.text.toString()
            getSharedPreferences("guess", MODE_PRIVATE)
                .edit()
                .putInt("REC_COUNTER", count)
                .putString("REC_NICKNAME", nickname)
                .apply()
            setResult(111)
            val intent = Intent()
            intent.putExtra("message","test")
            setResult(222,intent)
            finish()
        }
    }
}