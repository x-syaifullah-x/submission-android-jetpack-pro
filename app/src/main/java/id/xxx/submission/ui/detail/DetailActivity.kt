package id.xxx.submission.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import id.xxx.submission.R
import id.xxx.submission.base.BaseActivity
import id.xxx.submission.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    companion object {
        const val DATA_DESTINATION = 0
        const val DATA_ID = 1
        const val DATA_EXTRA = "DATA_EXTRA"
        const val DATA_EXTRA_ID = "DATA_EXTRA_ID"
    }

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var navController: NavController

    val viewModel by viewModels<DetailViewModel> { viewModelFactory }

    override val layoutActivity: Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getIntegerArrayListExtra(DATA_EXTRA)?.apply {
            viewModel.init(get(DATA_DESTINATION), get(DATA_ID))
        }

        navController = nav_host_detail_fragment.findNavController()
        savedInstanceState?.let { navController.restoreState(it) } ?: run {
            navController.navInflater.inflate(R.navigation.nav_graph_detail).run {
                startDestination = viewModel.getExtra(DATA_DESTINATION)
                navController.setGraph(this, bundleOf(DATA_EXTRA_ID to viewModel.getExtra(DATA_ID)))
            }
        }
    }
}