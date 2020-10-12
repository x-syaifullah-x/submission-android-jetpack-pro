package id.xxx.submission.data.source.local.entity.discover.tv

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    indices = [Index(value = [TvGenreEntity.FK])],
    foreignKeys = [ForeignKey(
        entity = TvResultEntity::class,
        parentColumns = [TvResultEntity.ID_TV_RESULT],
        childColumns = [TvGenreEntity.FK],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
class TvGenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk_tv_genre")
    val pk: Int? = null,

    @ColumnInfo(name = FK)
    val fk: Long,

    val genre: Int

) : Parcelable {
    companion object {
        const val FK = "foreign_tv_genre"
    }
}