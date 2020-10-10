package id.xxx.submission.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.LinearLayoutManager
import id.xxx.submission.R
import id.xxx.submission.base.BaseFragment
import id.xxx.submission.base.adapter.ItemClicked
import id.xxx.submission.databinding.FragmentMovieBinding
import id.xxx.submission.entity.DataEntity
import id.xxx.submission.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : BaseFragment<FragmentMovieBinding>(), ItemClicked<DataEntity> {
    private lateinit var adapterMovie: AdapterMovie

    override val fragmentLayout: Int = R.layout.fragment_movie

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterMovie = AdapterMovie().apply {
            setHasStableIds(true)
            onItemClicked = this@MovieFragment
            recycler_view.setHasFixedSize(true)
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = this
        }

        ViewModelProvider(this, NewInstanceFactory()).get(MovieViewModel::class.java).apply {
            adapterMovie.data = getDataMovie
        }
    }

    override fun onItemClick(entity: DataEntity) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.DATA_EXTRA, entity)
        }
        requireActivity().startActivity(intent)
    }
}