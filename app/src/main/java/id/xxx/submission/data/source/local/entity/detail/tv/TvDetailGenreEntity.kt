package id.xxx.submission.data.source.local.entity.detail.tv

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    indices = [Index(value = [TvDetailGenreEntity.FK])],
    foreignKeys = [ForeignKey(
        entity = TvDetailResponseEntity::class,
        parentColumns = [TvDetailResponseEntity.PK],
        childColumns = [TvDetailGenreEntity.FK],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
data class TvDetailGenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk_tv_detail_genre")
    val pk: Long? = null,

    val genre_code: Int,

    @ColumnInfo(name = FK)
    val fk: Long,

    val name: String
) : Parcelable {
    companion object {
        const val FK = "fk_tv_detail_genre"
    }
}