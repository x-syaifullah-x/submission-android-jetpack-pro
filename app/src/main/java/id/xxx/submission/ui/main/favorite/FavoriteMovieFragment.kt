package id.xxx.submission.ui.main.favorite

import android.content.Intent
import android.os.Bundle
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
import id.xxx.submission.ui.main.movie.AdapterMovie
import javax.inject.Inject

class FavoriteMovieFragment : BaseFragment<FragmentMovieBinding>(), ItemClicked<MovieResultModel> {

    companion object {
        private const val ARG_SECTION_NUMBER = "FAVORITE_MOVIE_FRAGMENT"

        fun newInstance(index: Int) = FavoriteMovieFragment().apply {
            arguments = Bundle().apply { putInt(ARG_SECTION_NUMBER, index) }
        }
    }

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nav_graph_main) { viewModelFactory }

    private lateinit var adapterMovie: AdapterMovie

    override val layoutFragment: Int = R.layout.fragment_movie

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterMovie = AdapterMovie().apply {
            onItemClicked = this@FavoriteMovieFragment
            binding.movieRecyclerView.setHasFixedSize(true)
            binding.movieRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.movieRecyclerView.adapter = this
        }
        viewModel.movieFav.observe(viewLifecycleOwner, { handleStat(it) })
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
}