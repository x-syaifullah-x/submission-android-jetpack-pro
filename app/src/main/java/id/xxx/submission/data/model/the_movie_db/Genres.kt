package id.xxx.submission.data.model.the_movie_db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genres(
    val genre_code: Int,
    val name: String
) : Parcelable