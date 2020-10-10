package id.xxx.submission.ui.tv

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.LinearLayoutManager
import id.xxx.submission.R
import id.xxx.submission.base.BaseFragment
import id.xxx.submission.base.adapter.ItemClicked
import id.xxx.submission.databinding.FragmentTvBinding
import id.xxx.submission.entity.DataEntity
import id.xxx.submission.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_movie.*

class TvFragment : BaseFragment<FragmentTvBinding>(), ItemClicked<DataEntity> {
    private lateinit var adapterTv: AdapterTv

    override val fragmentLayout: Int = R.layout.fragment_tv

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapterTv = AdapterTv().apply {
            setHasStableIds(true)
            onItemClicked = this@TvFragment
            recycler_view.setHasFixedSize(true)
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = this
        }

        ViewModelProvider(this, NewInstanceFactory()).get(TvViewModel::class.java).apply {
            adapterTv.data = getDataTv
        }
    }

    override fun onItemClick(entity: DataEntity) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.DATA_EXTRA, entity)
        }
        requireActivity().startActivity(intent)
    }
}