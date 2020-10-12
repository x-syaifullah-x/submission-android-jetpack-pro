package id.xxx.submission.data.source.local.entity.discover.movie

import androidx.room.Embedded
import androidx.room.Relation

data class MovieResultWithGenre(
    @Embedded
    val movieResultEntity: MovieResultEntity,

    @Relation(
        parentColumn = MovieResultEntity.ID_MOVIE_RESULT,
        entityColumn = MovieGenreEntity.FOREIGN_KEY_MOVIE_GENRE,
        entity = MovieGenreEntity::class
    )
    val genreIds: List<MovieGenreEntity>
)