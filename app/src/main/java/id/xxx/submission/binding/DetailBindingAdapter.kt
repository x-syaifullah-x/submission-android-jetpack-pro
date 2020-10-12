package id.xxx.submission.binding

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import id.xxx.submission.R.color.colorPrimary
import id.xxx.submission.data.model.the_movie_db.Genres
import id.xxx.submission.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_detail_tv.*

object DetailBindingAdapter {

    private lateinit var activity: DetailActivity

    private val onRefreshListener by lazy {
        SwipeRefreshLayout.OnRefreshListener {
            activity.swipe_refresh.isRefreshing = false
            val dataDes = activity.viewModel.getExtra(DetailActivity.DATA_DESTINATION)
            val dataId = activity.viewModel.getExtra(DetailActivity.DATA_ID)
            activity.viewModel.init(dataDes, dataId)
        }
    }

    private val onOffsetChangedListener by lazy {
        OnOffsetChangedListener { _, verticalOffset ->
            val ct = activity.collapsing_toolbar
            activity.swipe_refresh.isEnabled =
                (ct.height + verticalOffset) >= (5 * ViewCompat.getMinimumHeight(ct))
        }
    }

    private fun setColorDrawable(drawable: Drawable, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @JvmStatic
    @BindingAdapter("setup_app_bar_detail_activity")
    fun setAppBarLayoutDetail(view: View, url: String?) {
        activity = (view.context as DetailActivity)

        url?.apply {
            activity.progress_bar_collapsing_toolbar.visibility = VISIBLE
            Glide.with(view.context)
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
                        palette.getDominantColor(getColor(view.context, colorPrimary)).apply {
                            activity.window.statusBarColor = this
                            activity.collapsing_toolbar.setContentScrimColor(this)
                            setColorDrawable(activity.user_score_content.progressDrawable, this)
                        }
                        activity.progress_bar_collapsing_toolbar.visibility = GONE
                        activity.app_bar.addOnOffsetChangedListener(null)
                        activity.swipe_refresh.isEnabled = false
                        activity.app_bar.removeOnOffsetChangedListener(onOffsetChangedListener)
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        activity.progress_bar_collapsing_toolbar.visibility = GONE
                        activity.app_bar.addOnOffsetChangedListener(onOffsetChangedListener)
                        activity.swipe_refresh.setOnRefreshListener(onRefreshListener)
                        return false
                    }
                }).into(activity.poster_detail)
        } ?: run {
            activity.app_bar.addOnOffsetChangedListener(onOffsetChangedListener)
            activity.swipe_refresh.setOnRefreshListener(onRefreshListener)
        }
    }

    @JvmStatic
    @BindingAdapter("setup_fab_detail_activity")
    fun setFloatingAdd(view: View, isFavorite: Boolean?) {
        isFavorite?.apply {
            (view.context as AppCompatActivity).fab.setImageDrawable(
                ContextCompat.getDrawable(
                    view.context,
                    if (isFavorite) android.R.drawable.ic_menu_delete else android.R.drawable.ic_menu_add
                )
            )
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
}