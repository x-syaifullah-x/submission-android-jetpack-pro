package id.xxx.submission.ui.main.movie

import id.xxx.submission.R
import id.xxx.submission.base.adapter.BaseAdapter
import id.xxx.submission.data.source.remote.response.the_movie_db.MovieResult
import id.xxx.submission.databinding.ItemMovieBinding

class AdapterMovie : BaseAdapter<MovieResult, ItemMovieBinding>(R.layout.item_movie) {

    override fun onBindViewHolder(holder: Holder<ItemMovieBinding>, position: Int) {
        holder.binding?.let { imb ->
            imb.data = data[position]
            imb.root.setOnClickListener { onItemClicked?.onItemClick(data[position]) }
        }
    }
}