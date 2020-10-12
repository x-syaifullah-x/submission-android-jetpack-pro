package id.xxx.submission.data.source.remote.response

import id.xxx.submission.data.model.TvResultModel

data class TvResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<TvResultModel>
)