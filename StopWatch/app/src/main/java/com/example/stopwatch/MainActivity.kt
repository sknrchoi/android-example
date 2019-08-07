package com.example.stopwatch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    // 시간을 계산할 변수를 0으로 초기화
    private var time = 0
    private var timerTask: Timer? = null // null을 허용하는 Timer 타입으로 선언
    private var isRunning = false
    private var lap = 1 // 몇번째 랩인지 표시하기 위한 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 시작, 일시정지 FAB버튼에 이벤트 연결
        fab.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                start()
            } else {
                pause()
            }
        }

        // 랩 버튼 이벤트 연결
        lapButton.setOnClickListener {
            recordLapTime()
        }

        // 초기화 버튼 이벤트 연결
        resetFab.setOnClickListener {
            reset()
        }
    }

    // 시작 버튼 이벤트
    private fun start() {
        fab.setImageResource(R.drawable.ic_pause_black_24dp) // 타이머 시작 시 FAB의 이미지를 일시정지 이미지로 변경

        // 0.01초마다 time변수를 증가시키며 UI를 갱신함
        // timer는 워커 스레드에서 동작하여 UI 조작이 불가능함
        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread { // UI 조작하기 위한 메서드
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    // 일시중지 버튼 이벤트
    private fun pause() {
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp) // 타이머 일시정지 시 FAB의 이미지를 시작하는 이미지로 변경
        timerTask?.cancel() // 실행중인 타이머가 있다면 타이머를 취소함
    }

    // 랩 타임 기록 이벤트
    private fun recordLapTime() {
        val lapTime = this.time // 현재 시간을 지역변수에 저장
        val textView = TextView(this) // 동적으로 TextView를 생성
        textView.test = "$lap LAB : ${lapTime / 100}.${lapTime % 100}" // 동적으로 생성한 TextView에 '1LAB : 5.35'와 같은 형태의 문자열 설정

        // 맨 위부터 TextView 추가
        lapLayout.addView(textView, 0)
        lap ++
    }

    // 초기화 버튼 이벤트
    private fun reset() {
        timerTask?.cancel() // 실행중인 타이머가 있다면 타이머를 취소함

        // 모든 변수 초기화
        time = 0
        isRunning = false
        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"

        // 모든 랩타임 제거
        lapLayout.removeAllViews()
        lap = 1
    }
}
