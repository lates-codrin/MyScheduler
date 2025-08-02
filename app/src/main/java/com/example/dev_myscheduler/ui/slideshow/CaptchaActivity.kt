// CaptchaActivity.kt
package com.example.dev_myscheduler.ui.slideshow

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dev_myscheduler.R

class CaptchaActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var viewModel: StudentGradesViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captcha)

        webView = findViewById(R.id.webViewCaptcha)
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.clearCache(true)
        webView.clearHistory()

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.addJavascriptInterface(CaptchaInterface(), "Android")

        val sitekey = intent.getStringExtra("sitekey") ?: return

        val htmlData = """
            <!DOCTYPE html>
            <html>
              <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <script src="https://www.google.com/recaptcha/api.js"></script>
                <script>
                  function onSubmit(token) {
                    Android.onCaptchaSolved(token);
                  }
                </script>
              </head>
              <body>
                <form action="javascript:void(0);" onsubmit="onSubmit(grecaptcha.getResponse())">
                  <div class="g-recaptcha" data-sitekey="$sitekey" data-callback="onSubmit"></div>
                  <br/>
                  <button type="submit">Solve CAPTCHA</button>
                </form>
              </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL("https://www.google.com", htmlData, "text/html", "UTF-8", null)

        viewModel = ViewModelProvider(this).get(StudentGradesViewModel::class.java)
    }

    inner class CaptchaInterface {
        @JavascriptInterface
        fun onCaptchaSolved(token: String) {
            Log.d("CaptchaActivity", "CAPTCHA Token: $token")
            runOnUiThread {
                Toast.makeText(this@CaptchaActivity, "CAPTCHA Solved!", Toast.LENGTH_SHORT).show()

                val userId = "-"
                val username = "-"
                val password = "-"

                viewModel.solveCaptchaAndFetchGrades(userId, username, password,token)
            }
        }
    }
}
