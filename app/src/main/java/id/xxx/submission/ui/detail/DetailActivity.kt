package id.xxx.submission.ui.detail

import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.toDrawable
import androidx.palette.graphics.Palette
import id.xxx.submission.R
import id.xxx.submission.base.BaseActivity
import id.xxx.submission.databinding.ActivityDetailBinding
import id.xxx.submission.entity.DataEntity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.detail_content.*

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    override val layoutActivity: Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.getParcelableExtra<DataEntity>(DATA_EXTRA).apply {
            binding.content.data = this
        }

        setSupportActionBar(toolbar.apply { title = data?.title })
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        val bitmap: Bitmap = BitmapFactory.decodeStream(baseContext.openFileInput(data?.posterName))
        poster_detail.background = bitmap.toDrawable(baseContext.resources)

        val palette = Palette.Builder(bitmap).generate()
        palette.getDominantColor(getColor(baseContext, R.color.colorPrimary)).apply {
            window.statusBarColor = this
            collapsing_toolbar.setContentScrimColor(this)
            setColorDrawable(user_score_content.progressDrawable, this)
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

    companion object {
        const val DATA_EXTRA = "DATA_EXTRA"
    }
}