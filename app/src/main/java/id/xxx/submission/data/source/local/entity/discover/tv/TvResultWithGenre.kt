package id.xxx.submission.data.source.local.entity.discover.tv

import androidx.room.Embedded
import androidx.room.Relation

data class TvResultWithGenre(
    @Embedded
    val tvResultEntity: TvResultEntity,

    @Relation(
        parentColumn = TvResultEntity.ID_TV_RESULT,
        entityColumn = TvGenreEntity.FK,
        entity = TvGenreEntity::class
    )
    val genreIds: List<TvGenreEntity>
)