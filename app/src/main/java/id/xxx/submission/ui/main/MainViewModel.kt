package id.xxx.submission.ui.main

import androidx.lifecycle.ViewModel
import id.xxx.submission.data.TheMovieDBRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(repository: TheMovieDBRepository) : ViewModel() {

    val getDataMovie by lazy { repository.getDataMovie() }
    val getDataTv by lazy { repository.getDataTv() }
}