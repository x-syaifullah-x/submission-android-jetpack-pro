package id.xxx.submission.data.model.the_movie_db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpokenLanguages(
    val iso_639_1: String = "",
    val name: String = ""
) : Parcelable