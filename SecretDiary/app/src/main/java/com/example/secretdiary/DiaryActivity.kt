package com.example.secretdiary

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity: AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val diaryText = findViewById<EditText>(R.id.diaryText)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryText.setText(detailPreferences.getString("detail", ""))

        val runnable = Runnable {
            // 변경 내용 저장
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", diaryText.text.toString())
            }
        }


        // diaryText가 바뀔 때마다 호출
        diaryText.addTextChangedListener {

            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }

    }
}