package id.xxx.submission.data.source.local.entity.detail.movie

import androidx.room.Embedded
import androidx.room.Relation

data class MovieDetail(
    @Embedded
    val movieDetailResponseEntity: MovieDetailResponseEntity,

    @Relation(
        parentColumn = MovieDetailResponseEntity.PK,
        entityColumn = MovieDetailGenreEntity.FOREIGN_KEY,
        entity = MovieDetailGenreEntity::class
    )
    val genre: List<MovieDetailGenreEntity>
)