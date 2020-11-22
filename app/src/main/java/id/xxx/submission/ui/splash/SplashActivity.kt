package id.xxx.submission.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import id.xxx.submission.ui.main.MainActivity

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            .apply { finish() }
    }
}