package id.xxx.submission.data.source.local.entity.detail.tv

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvDetail(
    @Embedded
    val tvDetailResponseEntity: TvDetailResponseEntity,

    @Relation(
        parentColumn = TvDetailResponseEntity.PK,
        entityColumn = TvDetailGenreEntity.FK,
        entity = TvDetailGenreEntity::class
    )
    val genre: List<TvDetailGenreEntity>
) : Parcelable