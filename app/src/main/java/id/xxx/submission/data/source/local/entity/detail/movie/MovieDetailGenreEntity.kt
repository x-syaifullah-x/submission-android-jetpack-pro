package id.xxx.submission.data.source.local.entity.detail.movie

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    indices = [Index(value = [MovieDetailGenreEntity.FOREIGN_KEY])],
    foreignKeys = [ForeignKey(
        entity = MovieDetailResponseEntity::class,
        parentColumns = [MovieDetailResponseEntity.PK],
        childColumns = [MovieDetailGenreEntity.FOREIGN_KEY],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
data class MovieDetailGenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk_movie_detail_genre")
    val pk: Long? = null,

    val genre_code: Int,

    @ColumnInfo(name = FOREIGN_KEY)
    val fk: Long,

    val name: String
) : Parcelable {
    companion object {
        const val FOREIGN_KEY = "id_movie_detail_genre_foreign"
    }
}