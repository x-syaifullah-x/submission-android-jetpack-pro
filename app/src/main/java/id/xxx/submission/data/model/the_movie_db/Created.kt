package id.xxx.submission.data.model.the_movie_db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Created(
    val id: Int,
    val credit_id: String = "",
    val name: String = "",
    val gender: Int,
    val profile_path: String? = ""
) : Parcelable