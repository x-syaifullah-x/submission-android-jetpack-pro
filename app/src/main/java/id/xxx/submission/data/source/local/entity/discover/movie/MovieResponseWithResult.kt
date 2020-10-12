package id.xxx.submission.data.source.local.entity.discover.movie

import androidx.room.Embedded
import androidx.room.Relation

data class MovieResponseWithResult(
    @Embedded
    val movieResponseEntity: MovieResponseEntity,

    @Relation(
        parentColumn = MovieResponseEntity.ID_MOVIE_RESPONSE,
        entityColumn = MovieResultEntity.ID_MOVIE_RESULT_FOREIGN,
        entity = MovieResultEntity::class
    )
    val resultWithGenre: List<MovieResultWithGenre>
)