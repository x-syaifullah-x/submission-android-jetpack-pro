package id.xxx.submission.binding

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.View.GONE
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import id.xxx.submission.R.color.colorPrimary
import id.xxx.submission.data.source.remote.response.the_movie_db.Genres
import id.xxx.submission.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_detail_tv.*

object DetailBindingAdapter {

    private lateinit var detailActivity: DetailActivity

    private val onOffsetChangedListener by lazy {
        OnOffsetChangedListener { _, verticalOffset ->
            val ct = detailActivity.collapsing_toolbar
            detailActivity.swipe_refresh.isEnabled =
                (ct.height + verticalOffset) >= (5 * ViewCompat.getMinimumHeight(ct))
        }
    }

    @JvmStatic
    @BindingAdapter("setup_app_bar_detail_activity")
    fun setAppBarLayoutDetail(view: View, url: String?) {
        val ctx = view.context
        detailActivity = (ctx as DetailActivity)

        url?.apply {
            val pbCtl = detailActivity.progress_bar_collapsing_toolbar
            pbCtl.visibility = View.VISIBLE
            Glide.with(ctx)
                .load("https://image.tmdb.org/t/p/original/${url}")
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        val palette = Palette.Builder(resource!!.toBitmap()).generate()
                        palette.getDominantColor(getColor(ctx, colorPrimary)).apply {
                            detailActivity.window.statusBarColor = this
                            detailActivity.collapsing_toolbar.setContentScrimColor(this)
                            val usc = detailActivity.user_score_content
                            setColorDrawable(usc.progressDrawable, this)
                        }
                        pbCtl.visibility = GONE
                        detailActivity.app_bar.addOnOffsetChangedListener(null)
                        detailActivity.swipe_refresh.isEnabled = false
                        detailActivity.app_bar.removeOnOffsetChangedListener(onOffsetChangedListener)
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbCtl.visibility = GONE
                        detailActivity.app_bar.addOnOffsetChangedListener(onOffsetChangedListener)
                        return false
                    }
                }).into(detailActivity.poster_detail)
        } ?: run {
            detailActivity.app_bar.addOnOffsetChangedListener(onOffsetChangedListener)
        }
    }

    @JvmStatic
    @BindingAdapter("set_genre")
    fun setGenre(view: AppCompatTextView, it: List<Genres>?) {
        val genre = StringBuilder()
        it?.forEachIndexed { i, v ->
            genre.append(if (i != it.size - 1) "${v.name}, " else v.name)
        }
        view.text = genre.toString()
    }

    private fun setColorDrawable(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }
}