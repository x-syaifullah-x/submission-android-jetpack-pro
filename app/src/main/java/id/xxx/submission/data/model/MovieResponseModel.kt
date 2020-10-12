package id.xxx.submission.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieResponseModel(
    val page: Int = 0,
    val total_results: Int = 0,
    val total_pages: Int = 0,
    val results: List<MovieResultModel> = listOf()
) : Parcelable