package id.xxx.submission.ui.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.xxx.submission.data.Resource
import id.xxx.submission.data.model.MovieResultModel
import id.xxx.submission.data.model.TvResultModel
import id.xxx.submission.data.repository.MovieRepository
import id.xxx.submission.data.repository.TvRepository
import id.xxx.submission.data.source.local.dao.MovieDao
import id.xxx.submission.data.source.local.dao.TvDao
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val movieRepo: MovieRepository,
    private val tvRepo: TvRepository
) : ViewModel() {

    val movie = MediatorLiveData<Resource<PagedList<MovieResultModel>>>()
    val tv = MediatorLiveData<Resource<PagedList<TvResultModel>>>()
    val movieFav = MediatorLiveData<Resource<PagedList<MovieResultModel>>>()
    val tvFav = MediatorLiveData<Resource<PagedList<TvResultModel>>>()

    init {
        movie.addSource(movieRepo.getResult()) { movie.value = it }
        tv.addSource(tvRepo.getResult()) { tv.value = it }
        movieFav.addSource(movieRepo.getFavorite()) { movieFav.value = it }
        tvFav.addSource(tvRepo.getFavorite()) { tvFav.value = it }
    }

    fun sorting(type: Type) {
        when (type) {
            Type.NAME -> {
                movie.addSource(movieRepo.getResult(MovieDao.SORT_BY_NAME)) { movie.value = it }
                tv.addSource(tvRepo.getResult(TvDao.SORT_BY_NAME)) { tv.value = it }
                movieFav.addSource(movieRepo.getFavorite(MovieDao.SORT_FAV_BY_NAME)) {
                    movieFav.value = it
                }
                tvFav.addSource(tvRepo.getFavorite(TvDao.SORT_FAV_BY_NAME)) { tvFav.value = it }
            }
            Type.RELEASE_DATA -> {
                movie.addSource(movieRepo.getResult(MovieDao.SORT_BY_RELEASE)) { movie.value = it }
                tv.addSource(tvRepo.getResult(TvDao.SORT_BY_RELEASE)) { tv.value = it }
                movieFav.addSource(movieRepo.getFavorite(MovieDao.SORT_FAV_BY_RELEASE)) {
                    movieFav.value = it
                }
                tvFav.addSource(tvRepo.getFavorite(TvDao.SORT_FAV_BY_RELEASE)) { tvFav.value = it }
            }
        }
    }

    enum class Type {
        NAME, RELEASE_DATA
    }
}