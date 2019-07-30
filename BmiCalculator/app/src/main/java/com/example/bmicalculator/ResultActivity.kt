package com.example.bmicalculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // MainActivity로 부터 전달받은 키, 몸무게
        val height = intent.getStringExtra("height").toInt()
        val weight = intent.getStringExtra("weight").toInt()

        // 비만도(BMI) 계산
        val bmi = weight / Math.pow(height / 100.0, 2.0)

        // 수치와 비교하여 결과 표시
        when {
            bmi >= 35 -> resultTextView.text = "고도 비만"
            bmi >= 30 -> resultTextView.text = "2단계 비만"
            bmi >= 25 -> resultTextView.text = "1단계 비만"
            bmi >= 23 -> resultTextView.text = "과체중"
            bmi >= 18.5 -> resultTextView.text = "정상"
            else -> resultTextView.text = "저체중"
        }

        // 수치와 비교하여 이미지 표시
        when {
            bmi >= 23 ->
                imageView.setImageResource(
                    R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
            bmi >= 18.5 ->
                imageView.setImageResource(
                    R.drawable.ic_mood_black_24dp)
            else ->
                imageView.setImageResource(
                    R.drawable.ic_mood_bad_black_24dp)
        }

        // 토스트 코드 - 일반형
        //Toast.makeText(this, "$bmi", Toast.LENGTH_SHORT).show()

        // 토스트 코드 - Anko형
        toast("$bmi")
    }
}
