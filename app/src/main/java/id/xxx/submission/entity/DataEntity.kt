package id.xxx.submission.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataEntity(
    var title: String? = "",
    var releaseData: String? = "",
    var originalLanguage: String? = "",
    var originalCountry: String? = "",
    var voteAverage: String? = "",
    var overview: String? = "",
    var genre: String? = "",
    var posterName: String? = "",
) : Parcelable