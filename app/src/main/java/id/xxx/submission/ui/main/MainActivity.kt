package id.xxx.submission.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration.Builder
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import id.xxx.submission.R
import id.xxx.submission.base.BaseActivity
import id.xxx.submission.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutActivity: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        val navController = nav_host_main_fragment.findNavController()
        setupWithNavController(binding.bottomNavigationView, navController)
        setupActionBarWithNavController(
            navController, Builder(
                R.id.fragment_movie,
                R.id.fragment_tv,
                R.id.fragment_favorite
            ).build()
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return true
    }
}