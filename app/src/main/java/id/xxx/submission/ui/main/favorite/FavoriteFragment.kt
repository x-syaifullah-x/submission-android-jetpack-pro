package id.xxx.submission.ui.main.favorite

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import id.xxx.submission.R
import id.xxx.submission.base.BaseFragment
import id.xxx.submission.databinding.FragmentFavoriteBinding
import id.xxx.submission.ui.main.MainViewModel
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nav_graph_main) { viewModelFactory }

    override val layoutFragment = R.layout.fragment_favorite

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewPager.adapter = FavoritePagerAdapter(requireContext(), childFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.by_name -> {
                item.isChecked = true
                viewModel.sorting(MainViewModel.Type.NAME);true
            }
            R.id.by_release -> {
                item.isChecked = true
                viewModel.sorting(MainViewModel.Type.RELEASE_DATA);true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}