package id.xxx.submission.ui.main.movie

import androidx.recyclerview.widget.DiffUtil
import id.xxx.submission.R
import id.xxx.submission.base.adapter.BaseAdapter
import id.xxx.submission.data.model.MovieResultModel
import id.xxx.submission.databinding.ItemMovieBinding

class AdapterMovie :
    BaseAdapter<MovieResultModel, ItemMovieBinding>(R.layout.item_movie, diffUtil) {
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MovieResultModel>() {
            override fun areItemsTheSame(oldItem: MovieResultModel, newItem: MovieResultModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieResultModel, newItem: MovieResultModel) =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: Holder<ItemMovieBinding>, position: Int) {
        holder.binding?.let { imb ->
            getItem(position)?.apply {
                imb.data = this
                imb.root.setOnClickListener { onItemClicked?.onItemClick(this) }
            }
        }
    }
}