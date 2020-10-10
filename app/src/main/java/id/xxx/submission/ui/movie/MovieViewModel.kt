package id.xxx.submission.ui.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.xxx.submission.data.ResourcesData
import id.xxx.submission.entity.DataEntity

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    val getDataMovie: ArrayList<DataEntity> = ResourcesData.getDataMovie(getApplication())
}