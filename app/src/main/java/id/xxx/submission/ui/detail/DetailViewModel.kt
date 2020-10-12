package id.xxx.submission.ui.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import id.xxx.submission.R
import id.xxx.submission.data.Resource
import id.xxx.submission.data.model.MovieDetailModel
import id.xxx.submission.data.model.TvDetailModel
import id.xxx.submission.data.repository.MovieRepository
import id.xxx.submission.data.repository.TvRepository
import id.xxx.submission.ui.detail.DetailActivity.Companion.DATA_DESTINATION
import id.xxx.submission.ui.detail.DetailActivity.Companion.DATA_ID
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val movieRepo: MovieRepository, private val tvRepo: TvRepository
) : ViewModel() {

    private lateinit var dataExtra: MutableList<Int>

    val movie by lazy { MediatorLiveData<Resource<MovieDetailModel>>() }
    val tv by lazy { MediatorLiveData<Resource<TvDetailModel>>() }

    fun init(dataDes: Int, dataId: Int) {
        dataExtra = mutableListOf(dataDes, dataId)
        when (dataExtra[DATA_DESTINATION]) {
            R.id.detail_movie -> movie.addSource(movieRepo.getDetail(dataExtra[DATA_ID])) {
                movie.value = it
            }
            R.id.detail_tv -> tv.addSource(tvRepo.getDetail(dataExtra[DATA_ID])) {
                tv.value = it
            }
        }
    }

    fun getExtra(data: Int) = this.dataExtra[data]

    fun setFavoriteMovie(id: Int, isFavorite: Boolean) =
        movieRepo.setFavorite(id, isFavorite)

    fun setFavoriteTv(id: Int, isFavorite: Boolean) =
        tvRepo.setFavorite(id, isFavorite)
}