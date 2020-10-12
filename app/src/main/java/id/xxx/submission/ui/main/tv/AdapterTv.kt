package id.xxx.submission.ui.main.tv

import androidx.recyclerview.widget.DiffUtil
import id.xxx.submission.R
import id.xxx.submission.base.adapter.BaseAdapter
import id.xxx.submission.data.model.TvResultModel
import id.xxx.submission.databinding.ItemTvBinding

class AdapterTv : BaseAdapter<TvResultModel, ItemTvBinding>(R.layout.item_tv, diffUtil) {
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<TvResultModel>() {
            override fun areItemsTheSame(oldItem: TvResultModel, newItem: TvResultModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TvResultModel, newItem: TvResultModel) =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: Holder<ItemTvBinding>, position: Int) {
        holder.binding?.let { imb ->
            getItem(position)?.apply {
                imb.data = this
                imb.root.setOnClickListener { onItemClicked?.onItemClick(this) }
            }
        }
    }
}