package id.xxx.submission.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.xxx.submission.R
import id.xxx.submission.base.BaseActivity
import id.xxx.submission.databinding.ActivityDetailBinding
import id.xxx.submission.ui.detail.DetailViewModel.Companion.DATA_DESTINATION
import id.xxx.submission.ui.detail.DetailViewModel.Companion.DATA_ID
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseActivity<ActivityDetailBinding>(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var navController: NavController

    private val viewModel by viewModels<DetailViewModel> { viewModelFactory }

    override val layoutActivity: Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getIntegerArrayListExtra(DATA_EXTRA)?.apply {
            viewModel.setDataExtra(get(DATA_DESTINATION), get(DATA_ID))
        }

        navController = nav_host_detail_fragment.findNavController()
        savedInstanceState?.let {
            navController.restoreState(it)
        } ?: run {
            navController.navInflater.inflate(R.navigation.nav_graph_detail).run {
                startDestination = viewModel.getDataExtra(DATA_DESTINATION)
                navController
                    .setGraph(this, bundleOf(DATA_EXTRA_ID to viewModel.getDataExtra(DATA_ID)))
            }
        }
        binding.swipeRefresh.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        swipe_refresh.isRefreshing = false
        viewModel.dataTv = null
        viewModel.dataMovie = null
        val dataDes = viewModel.getDataExtra(DATA_DESTINATION)
        val dataId = viewModel.getDataExtra(DATA_ID)
        viewModel.setDataExtra(dataDes, dataId)
        navController.navigate(dataDes, bundleOf(DATA_EXTRA_ID to dataId))
    }

    companion object {
        const val DATA_EXTRA = "DATA_EXTRA"
        const val DATA_EXTRA_ID = "DATA_EXTRA_ID"
    }
}