package id.xxx.submission.data.repository

import androidx.paging.PagedList

fun config(pageSize: Int = 5): PagedList.Config = PagedList.Config.Builder()
    .setEnablePlaceholders(false).setPageSize(pageSize).build()