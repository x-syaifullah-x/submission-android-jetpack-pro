package id.xxx.submission.ui.detail

import android.app.AlertDialog
import android.content.Context
import id.xxx.submission.R

fun dialogAddRemove(context: Context, title: String?, isFavorite: Boolean, `fun`: () -> Unit) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage("Do you want to ${if (isFavorite) "delete" else "add"} $title in favorite")
        .setNegativeButton(context.getString(R.string.cancel), null)
        .setPositiveButton(context.getString(R.string.ok)) { _, _ ->
            `fun`.invoke()
        }.show()
}