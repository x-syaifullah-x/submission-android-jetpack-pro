package id.xxx.submission.base

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<ActivityBinding : ViewDataBinding> : DaggerAppCompatActivity() {

    protected abstract val layoutActivity: Int
    protected lateinit var binding: ActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutActivity)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment = supportFragmentManager.fragments.first() as NavHostFragment
        val fragment = navHostFragment.childFragmentManager.fragments
        fragment.forEach { it.onOptionsItemSelected(item) }
        return super.onOptionsItemSelected(item)
    }
}