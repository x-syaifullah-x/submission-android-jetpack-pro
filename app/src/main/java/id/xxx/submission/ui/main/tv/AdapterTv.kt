package id.xxx.submission.ui.main.tv

import id.xxx.submission.R
import id.xxx.submission.base.adapter.BaseAdapter
import id.xxx.submission.data.source.remote.response.the_movie_db.TvResult
import id.xxx.submission.databinding.ItemTvBinding

class AdapterTv : BaseAdapter<TvResult, ItemTvBinding>(R.layout.item_tv) {

    override fun onBindViewHolder(holder: Holder<ItemTvBinding>, position: Int) {
        holder.binding?.let { imb ->
            imb.data = data[position]
            imb.root.setOnClickListener { onItemClicked?.onItemClick(data[position]) }
        }
    }
}