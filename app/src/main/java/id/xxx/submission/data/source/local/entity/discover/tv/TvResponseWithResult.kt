package id.xxx.submission.data.source.local.entity.discover.tv

import androidx.room.Embedded
import androidx.room.Relation

data class TvResponseWithResult(
    @Embedded
    val tvResponseEntity: TvResponseEntity,

    @Relation(
        parentColumn = TvResponseEntity.ID_TV_RESPONSE,
        entityColumn = TvResultEntity.ID_TV_RESULT_FOREIGN,
        entity = TvResultEntity::class
    )
    val result: List<TvResultWithGenre>
)