package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 웹뷰 기본 설정
        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        webView.loadUrl("http://www.google.com")

        // 키보드의 검색버튼 동작 정의
        urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                webView.loadUrl(urlEditText.text.toString())
                true
            } else {
                false
            }
        }


    }

    // 뒤로가기 버튼 동작 정의
    override fun onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("http://www.googole.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.net")
                return true
            }
            R.id.action_call -> {
                // 암시적 인텐트
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-12-4567")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                // 문자 보내기
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("sms:010-1234-4567")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }

                return true
            }
            R.id.action_email -> {
                // 이메일 보내기
                var intent = Intent(Intent.ACTION_SEND)
                intent.setType("plain/Text")
                intent.putExtra(Intent.EXTRA_EMAIL, "test@test.com")
                intent.putExtra(Intent.EXTRA_SUBJECT, "")
                intent.putExtra(Intent.EXTRA_TEXT, "")
                intent.setType("message/rfc822")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(Intent.createChooser(intent, ""))
                }

                return true
            }
        }
        /**
         * NOTE:
         * ACTION_XXX에 대한 공급자를 가지고있는 것이 아니기 때문에 resolveActivity()를 확인하지 않고 startActivity시
         * ActivityNotFoundException를 발생시킬 수 있기 때문에 조건문으로 확인 후 startActivity한다.
         */
        return super.onOptionsItemSelected(item)
    }
}

