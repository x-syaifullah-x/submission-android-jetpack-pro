package id.xxx.submission

import android.os.Bundle
import android.view.Gravity.CENTER
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams.MATCH_PARENT
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class SingleFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, CENTER)
            id = R.id.container
            setContentView(this)
        }
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.commit { add(R.id.container, fragment, "TEST") }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit { add(R.id.container, fragment) }
    }
}