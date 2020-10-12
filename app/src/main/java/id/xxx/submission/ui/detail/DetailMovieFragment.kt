package id.xxx.submission.ui.detail

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import id.xxx.submission.R
import id.xxx.submission.base.BaseFragment
import id.xxx.submission.data.Resource
import id.xxx.submission.data.model.MovieDetailModel
import id.xxx.submission.databinding.FragmentDetailMovieBinding
import kotlinx.android.synthetic.main.activity_detail.*

class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding>() {

    private val viewModel by activityViewModels<DetailViewModel>()

    override val layoutFragment: Int = R.layout.fragment_detail_movie

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.movie.observe(viewLifecycleOwner, { handleStat(it) })

        requireActivity().fab.setOnClickListener {
            val data = binding.data ?: return@setOnClickListener
            dialogAddRemove(requireContext(), data.title, data.isFavorite) {
                viewModel.setFavoriteMovie(data.id, !data.isFavorite)
            }
        }
    }

    private fun handleStat(resource: Resource<MovieDetailModel>) {
        val pb = requireActivity().progress_bar
        val collapsingToolbar = requireActivity().collapsing_toolbar
        when (resource) {
            is Resource.Loading -> {
                pb.visibility = VISIBLE
            }
            is Resource.Empty -> collapsingToolbar.title = "Data Not Found"
            is Resource.Success -> {
                pb.visibility = GONE
                resource.data.let { data ->
                    collapsingToolbar.title = data.title
                    binding.data = data
                }
            }
            is Resource.Error -> {
                collapsingToolbar.title = "Error"
                pb.visibility = GONE
                Toast.makeText(requireContext(), resource.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}