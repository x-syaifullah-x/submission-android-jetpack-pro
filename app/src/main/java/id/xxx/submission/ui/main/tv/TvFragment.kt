package id.xxx.submission.ui.main.tv

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.xxx.submission.R
import id.xxx.submission.base.BaseFragment
import id.xxx.submission.base.adapter.ItemClicked
import id.xxx.submission.data.Resource
import id.xxx.submission.data.source.remote.response.TvResponse
import id.xxx.submission.data.source.remote.response.the_movie_db.TvResult
import id.xxx.submission.databinding.FragmentTvBinding
import id.xxx.submission.ui.detail.DetailActivity
import id.xxx.submission.ui.main.MainViewModel
import javax.inject.Inject

class TvFragment : BaseFragment<FragmentTvBinding>(), ItemClicked<TvResult> {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapterTv: AdapterTv
    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nav_graph_main) { viewModelFactory }

    override val layoutFragment: Int = R.layout.fragment_tv

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterTv = AdapterTv().apply {
            setHasStableIds(true)
            onItemClicked = this@TvFragment
            binding.tvRecyclerView.setHasFixedSize(true)
            binding.tvRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.tvRecyclerView.adapter = this
        }

        viewModel.getDataTv.observe(viewLifecycleOwner, { handleStat(it) })
    }

    private fun handleStat(resource: Resource<TvResponse>) {
        when (resource) {
            is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
            is Resource.Empty -> binding.progressBar.visibility = View.GONE
            is Resource.Success -> {
                binding.progressBar.visibility = View.GONE
                resource.data.let { data -> adapterTv.data = data.results.toMutableList() }
            }
            is Resource.Error -> {
                findNavController().getViewModelStoreOwner(R.id.nav_graph_main).viewModelStore.clear()
                viewModelStore.clear()
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), resource.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(entity: TvResult) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.DATA_EXTRA, arrayListOf(R.id.detail_tv, entity.id))
        }
        requireActivity().startActivity(intent)
    }
}