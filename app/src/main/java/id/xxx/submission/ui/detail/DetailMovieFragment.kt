package id.xxx.submission.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import id.xxx.submission.R
import id.xxx.submission.base.BaseFragment
import id.xxx.submission.data.Resource
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.databinding.FragmentDetailMovieBinding
import kotlinx.android.synthetic.main.activity_detail.*

class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding>() {

    private val viewModel by activityViewModels<DetailViewModel>()

    override val layoutFragment: Int = R.layout.fragment_detail_movie

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.dataMovie?.observe(viewLifecycleOwner, { handleStat(it) })
    }

    private fun handleStat(resource: Resource<MovieDetailResponse>) {
        val pb = requireActivity().progress_bar
        val collapsingToolbar = requireActivity().collapsing_toolbar
        when (resource) {
            is Resource.Loading -> pb.visibility = View.VISIBLE
            is Resource.Empty -> collapsingToolbar.title = "Data Not Found"
            is Resource.Success -> {
                pb.visibility = View.GONE
                resource.data.let { data ->
                    collapsingToolbar.title = data.title
                    binding.data = data
                }
            }
            is Resource.Error -> {
                collapsingToolbar.title = "Error"
                pb.visibility = View.GONE
                Toast.makeText(requireContext(), resource.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}