package com.example.secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

 // 각각의 NumberPicker 값을 0~9 사이로 지정
    private val numberPicker1: NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply{
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker2: NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply{
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker3: NumberPicker by lazy{
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply{
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: Button by lazy {
        findViewById<Button>(R.id.openButton)
    }

    private val changePassword: Button by lazy {
        findViewById<Button>(R.id.changePassword)
    }

    // 비밀번호 변경모드 일때 다른 작업을 할 수 없도록 하기위한 boolean 변수 선언
    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        // 패스워드 입력 후 diary 오픈
        openButton.setOnClickListener {
            if(changePasswordMode){
                Toast.makeText(this,"비밀번호 변경 중입니다.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // SharePreference
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            // NumberPicker 3개의 값을 이어붙인 것을 유저가 입력한 패스워드로 지정
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreferences.getString("password","000").equals(passwordFromUser)){
                // 패스워드 맞음
                startActivity(Intent(this, DiaryActivity::class.java))
            }else{
                //패스워드 틀림
                showErrorAlertDialog()
            }
        }

        // 패스워드 바꾸기
        changePassword.setOnClickListener {
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(changePasswordMode){
                // 변경된 패스워드 저장
                passwordPreferences.edit(true){
                    putString("password", passwordFromUser)
                }
                changePasswordMode = false
                changePassword.setBackgroundColor(Color.BLACK)

            }else{

                if (passwordPreferences.getString("password","000").equals(passwordFromUser)){

                    changePasswordMode = true
                    Toast.makeText(this,"변경할 패스워드를 입력해주세요.",Toast.LENGTH_SHORT).show()

                    changePassword.setBackgroundColor(Color.RED)
                }else{
                    //패스워드 틀림
                    showErrorAlertDialog()
                }

            }
        }

    }

    // 패스워드가 틀렸을 때 오류 메시지
    private fun showErrorAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인"){ _, _ -> }
            .create()
            .show()
    }
}