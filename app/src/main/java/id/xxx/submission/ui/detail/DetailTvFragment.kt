package id.xxx.submission.ui.detail

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import id.xxx.submission.R
import id.xxx.submission.base.BaseFragment
import id.xxx.submission.data.Resource
import id.xxx.submission.data.model.TvDetailModel
import id.xxx.submission.databinding.FragmentDetailTvBinding
import kotlinx.android.synthetic.main.activity_detail.*

class DetailTvFragment : BaseFragment<FragmentDetailTvBinding>() {

    private val viewModel by activityViewModels<DetailViewModel>()

    override val layoutFragment: Int = R.layout.fragment_detail_tv

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.tv.observe(requireActivity(), { handleStat(it) })

        requireActivity().fab.setOnClickListener {
            val data = binding.data ?: return@setOnClickListener
            dialogAddRemove(requireContext(), data.original_name, data.isFavorite) {
                viewModel.setFavoriteTv(data.id, !data.isFavorite)
            }
        }
    }

    private fun handleStat(resource: Resource<TvDetailModel>) {
        val pb = requireActivity().progress_bar
        val collapsingToolbar = requireActivity().collapsing_toolbar
        when (resource) {
            is Resource.Loading -> pb.visibility = VISIBLE
            is Resource.Empty -> collapsingToolbar.title = "Data Not Found"
            is Resource.Success -> {
                pb.visibility = GONE
                resource.data.let { data ->
                    collapsingToolbar.title = data.original_name
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