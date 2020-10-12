package id.xxx.submission.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TvResponseModel(
    val page: Int = 0,
    val total_results: Int = 0,
    val total_pages: Int = 0,
    val results: List<TvResultModel> = listOf()
) : Parcelable