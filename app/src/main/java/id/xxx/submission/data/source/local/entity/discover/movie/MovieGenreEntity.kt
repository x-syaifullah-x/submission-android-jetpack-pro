package id.xxx.submission.data.source.local.entity.discover.movie

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    indices = [Index(value = [MovieGenreEntity.FOREIGN_KEY_MOVIE_GENRE])],
    foreignKeys = [ForeignKey(
        entity = MovieResultEntity::class,
        parentColumns = [MovieResultEntity.ID_MOVIE_RESULT],
        childColumns = [MovieGenreEntity.FOREIGN_KEY_MOVIE_GENRE],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
    )]
)
class MovieGenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk_genre")
    val pk: Int? = null,

    @ColumnInfo(name = "foreign_key_movie_genre")
    val fk: Long,

    @ColumnInfo(name = "genre_code")
    val genre: Int

) : Parcelable {
    companion object {
        const val FOREIGN_KEY_MOVIE_GENRE = "foreign_key_movie_genre"
    }
}