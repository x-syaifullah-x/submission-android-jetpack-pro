package id.xxx.submission.data.model.the_movie_db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BelongsToCollection(
    val id: Int,
    val name: String,
    val poster_path: String,
    val backdrop_path: String
) : Parcelable