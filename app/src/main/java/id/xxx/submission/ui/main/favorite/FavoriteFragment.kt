package id.xxx.submission.ui.main.favorite

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.navGraphViewModels
import id.xxx.submission.App
import id.xxx.submission.R
import id.xxx.submission.base.BaseFragment
import id.xxx.submission.databinding.FragmentFavoriteBinding
import id.xxx.submission.ui.main.MainViewModel
import id.xxx.submission.viewmodel.ViewModelFactory
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    @Inject
    internal lateinit var factory: ViewModelFactory

    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nav_graph_main) { factory }

    override val layoutFragment = R.layout.fragment_favorite

    override fun onAttach(context: Context) {
        (requireActivity().application as App).appComponent.inject(this)
        super.onAttach(context)
    }

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