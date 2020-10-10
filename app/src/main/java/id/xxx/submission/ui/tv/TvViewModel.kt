package id.xxx.submission.ui.tv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.xxx.submission.data.ResourcesData
import id.xxx.submission.entity.DataEntity

class TvViewModel(application: Application) : AndroidViewModel(application) {
    val getDataTv: ArrayList<DataEntity> = ResourcesData.getDataTv(getApplication())
}