package id.xxx.submission.ui.movie

import id.xxx.submission.R
import id.xxx.submission.base.adapter.BaseAdapter
import id.xxx.submission.databinding.ItemBinding
import id.xxx.submission.entity.DataEntity

class AdapterMovie : BaseAdapter<DataEntity, ItemBinding>(R.layout.item) {

    override fun onBindViewHolder(holder: Holder<ItemBinding>, position: Int) {
        holder.binding?.let { imb ->
            imb.data = data[position]
            imb.root.setOnClickListener { onItemClicked?.onItemClick(data[position]) }
        }
    }
}