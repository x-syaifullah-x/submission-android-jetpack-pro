package id.xxx.submission.ui.main.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import id.xxx.submission.R
import id.xxx.submission.base.BaseFragment
import id.xxx.submission.base.adapter.ItemClicked
import id.xxx.submission.data.Resource
import id.xxx.submission.data.model.MovieResultModel
import id.xxx.submission.databinding.FragmentMovieBinding
import id.xxx.submission.ui.detail.DetailActivity
import id.xxx.submission.ui.main.MainViewModel
import javax.inject.Inject

class MovieFragment : BaseFragment<FragmentMovieBinding>(), ItemClicked<MovieResultModel> {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nav_graph_main) { viewModelFactory }

    private lateinit var adapterMovie: AdapterMovie

    override val layoutFragment: Int = R.layout.fragment_movie

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterMovie = AdapterMovie().apply {
            onItemClicked = this@MovieFragment
            binding.movieRecyclerView.setHasFixedSize(true)
            binding.movieRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.movieRecyclerView.adapter = this
        }
        viewModel.movie.observe(viewLifecycleOwner, { handleStat(it) })
    }

    private fun handleStat(resource: Resource<PagedList<MovieResultModel>>) {
        when (resource) {
            is Resource.Loading -> binding.movieProgressBar.visibility = View.VISIBLE
            is Resource.Success -> {
                binding.movieProgressBar.visibility = View.GONE
                resource.data.let { data -> adapterMovie.submitList(data) }
            }
            is Resource.Empty -> {
                binding.movieProgressBar.visibility = View.GONE
            }
            is Resource.Error -> {
                Log.i("TAG", "handleStat: error")
                findNavController().getViewModelStoreOwner(R.id.nav_graph_main).viewModelStore.clear()
                binding.movieProgressBar.visibility = View.GONE
                Toast.makeText(requireContext(), resource.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClick(model: MovieResultModel) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.DATA_EXTRA, arrayListOf(R.id.detail_movie, model.id))
        }
        requireActivity().startActivity(intent)
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