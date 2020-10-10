package id.xxx.submission.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import id.xxx.submission.R
import id.xxx.submission.entity.DataEntity
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream

object ResourcesData {
    private const val TITLE = 0
    private const val RELEASE_DATE = 1
    private const val ORIGINAL_LANGUAGE = 2
    private const val ORIGINAL_COUNTRY = 3
    private const val VOTE_AVERAGE = 4
    private const val OVERVIEW = 5
    private const val GENRE = 6

    private val movie = ArrayList<DataEntity>()
    private val tv = ArrayList<DataEntity>()

    fun getDataMovie(ctx: Context): ArrayList<DataEntity> {
        movie.clear()
        addMovie(
            ctx,
            resourcesData(ctx, R.array.alita),
            resourcePoster(ctx, R.drawable.poster_alita)
        )
        addMovie(
            ctx,
            resourcesData(ctx, R.array.aquaman),
            resourcePoster(ctx, R.drawable.poster_aquaman)
        )
        addMovie(
            ctx,
            resourcesData(ctx, R.array.bohemian),
            resourcePoster(ctx, R.drawable.poster_bohemian)
        )
        addMovie(
            ctx,
            resourcesData(ctx, R.array.cold_pursuit),
            resourcePoster(ctx, R.drawable.poster_cold_persuit)
        )
        addMovie(
            ctx,
            resourcesData(ctx, R.array.creed_II),
            resourcePoster(ctx, R.drawable.poster_creed)
        )
        addMovie(
            ctx,
            resourcesData(ctx, R.array.glass),
            resourcePoster(ctx, R.drawable.poster_glass)
        )
        addMovie(
            ctx,
            resourcesData(ctx, R.array.avengers),
            resourcePoster(ctx, R.drawable.poster_infinity_war)
        )
        addMovie(
            ctx,
            resourcesData(ctx, R.array.mortal_engines),
            resourcePoster(ctx, R.drawable.poster_mortal_engines)
        )
        addMovie(
            ctx,
            resourcesData(ctx, R.array.robin_hood),
            resourcePoster(ctx, R.drawable.poster_robin_hood)
        )
        addMovie(
            ctx,
            resourcesData(ctx, R.array.t34),
            resourcePoster(ctx, R.drawable.poster_t34)
        )
        return movie
    }

    fun getDataTv(context: Context): ArrayList<DataEntity> {
        tv.clear()
        addTv(
            context,
            resourcesData(context, R.array.arrow),
            resourcePoster(context, R.drawable.poster_arrow)
        )
        addTv(
            context,
            resourcesData(context, R.array.doom_patrol),
            resourcePoster(context, R.drawable.poster_doom_patrol)
        )
        addTv(
            context,
            resourcesData(context, R.array.dragon_ball),
            resourcePoster(context, R.drawable.poster_dragon_ball)
        )
        addTv(
            context,
            resourcesData(context, R.array.family_guy),
            resourcePoster(context, R.drawable.poster_family_guy)
        )
        addTv(
            context,
            resourcesData(context, R.array.the_flash),
            resourcePoster(context, R.drawable.poster_flash)
        )
        addTv(
            context,
            resourcesData(context, R.array.gotham),
            resourcePoster(context, R.drawable.poster_gotham)
        )
        addTv(
            context,
            resourcesData(context, R.array.grey_s_anatomy),
            resourcePoster(context, R.drawable.poster_grey_anatomy)
        )
        addTv(
            context,
            resourcesData(context, R.array.marvel_s_iron_fist),
            resourcePoster(context, R.drawable.poster_iron_fist)
        )
        addTv(
            context,
            resourcesData(context, R.array.ncis),
            resourcePoster(context, R.drawable.poster_ncis)
        )
        addTv(
            context,
            resourcesData(context, R.array.riverdale),
            resourcePoster(context, R.drawable.poster_riverdale)
        )
        return tv
    }

    private fun addMovie(ctx: Context, data: Array<out String>, poster: Drawable?) {
        movie.add(
            DataEntity(
                data[TITLE],
                data[RELEASE_DATE],
                data[ORIGINAL_LANGUAGE],
                data[ORIGINAL_COUNTRY],
                data[VOTE_AVERAGE],
                data[GENRE],
                data[OVERVIEW],
                createImageFromBitmap(data[TITLE], poster?.toBitmap(), ctx)
            )
        )
    }

    private fun addTv(ctx: Context, data: Array<out String>, poster: Drawable?) {
        tv.add(
            DataEntity(
                data[TITLE],
                data[RELEASE_DATE],
                data[ORIGINAL_LANGUAGE],
                data[ORIGINAL_COUNTRY],
                data[VOTE_AVERAGE],
                data[GENRE],
                data[OVERVIEW],
                createImageFromBitmap(data[TITLE], poster?.toBitmap(), ctx)
            )
        )
    }

    private fun resourcesData(ctx: Context, array: Int): Array<out String> {
        return ctx.resources.getStringArray(array)
    }

    private fun resourcePoster(ctx: Context, int: Int): Drawable? {
        return ContextCompat.getDrawable(ctx, int)
    }

    private fun createImageFromBitmap(name:String, bitmap: Bitmap?, context: Context): String? {
        return try {
            val bytes = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val fo: FileOutputStream = context.openFileOutput(name.trim(), Context.MODE_PRIVATE)
            fo.write(bytes.toByteArray())
            fo.close()
            name
        } catch (e: Exception) {
            null
        }
    }
}