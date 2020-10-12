package id.xxx.submission.utils

import id.xxx.submission.data.model.MovieDetailModel
import id.xxx.submission.data.model.MovieResultModel
import id.xxx.submission.data.model.TvDetailModel
import id.xxx.submission.data.model.TvResultModel
import id.xxx.submission.data.model.the_movie_db.Genres
import id.xxx.submission.data.source.local.entity.detail.movie.MovieDetail
import id.xxx.submission.data.source.local.entity.detail.movie.MovieDetailResponseEntity
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetail
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetailResponseEntity
import id.xxx.submission.data.source.local.entity.discover.movie.MovieResponseEntity
import id.xxx.submission.data.source.local.entity.discover.movie.MovieResultEntity
import id.xxx.submission.data.source.local.entity.discover.movie.MovieResultWithGenre
import id.xxx.submission.data.source.local.entity.discover.tv.TvResponseEntity
import id.xxx.submission.data.source.local.entity.discover.tv.TvResultEntity
import id.xxx.submission.data.source.local.entity.discover.tv.TvResultWithGenre
import id.xxx.submission.data.source.remote.response.MovieDetailResponse
import id.xxx.submission.data.source.remote.response.MovieResponse
import id.xxx.submission.data.source.remote.response.TvDetailResponse
import id.xxx.submission.data.source.remote.response.TvResponse

object DataMapper {

    fun listMovieResultWithGenreToListMovieResultModel(list: MutableList<MovieResultWithGenre>?): List<MovieResultModel> {
        val listResult = mutableListOf<MovieResultModel>()
        list?.forEach {
            listResult.add(MovieResultModel(
                vote_average = it.movieResultEntity.vote_average,
                video = it.movieResultEntity.video,
                popularity = it.movieResultEntity.popularity,
                vote_count = it.movieResultEntity.vote_count,
                poster_path = it.movieResultEntity.poster_path,
                original_title = it.movieResultEntity.original_title,
                overview = it.movieResultEntity.overview,
                release_date = it.movieResultEntity.release_date,
                title = it.movieResultEntity.title,
                original_language = it.movieResultEntity.original_language,
                backdrop_path = it.movieResultEntity.backdrop_path,
                id = it.movieResultEntity.pk,
                adult = it.movieResultEntity.adult,
                genre_ids = it.genreIds.map { genreIds -> genreIds.genre }
            ))
        }
        return listResult
    }

    fun listTvResultWithGenreToListTvResultModel(list: MutableList<TvResultWithGenre>?): List<TvResultModel> {
        val listResult = mutableListOf<TvResultModel>()
        list?.forEach {
            listResult.add(
                TvResultModel(
                    vote_average = it.tvResultEntity.vote_average,
                    popularity = it.tvResultEntity.popularity,
                    vote_count = it.tvResultEntity.vote_count,
                    poster_path = it.tvResultEntity.poster_path,
                    overview = it.tvResultEntity.overview,
                    original_language = it.tvResultEntity.original_language,
                    backdrop_path = it.tvResultEntity.backdrop_path,
                    genre_ids = it.genreIds.map { genreIds -> genreIds.genre },
                    name = it.tvResultEntity.name,
                    first_air_date = it.tvResultEntity.first_air_date,
                    original_name = it.tvResultEntity.original_name,
                    id = it.tvResultEntity.id_tv_result
                )
            )
        }
        return listResult
    }

    fun tvResultToTvResultModel(tvResultWithGenre: TvResultWithGenre): TvResultModel {
        return TvResultModel(
            vote_average = tvResultWithGenre.tvResultEntity.vote_average,
            popularity = tvResultWithGenre.tvResultEntity.popularity,
            vote_count = tvResultWithGenre.tvResultEntity.vote_count,
            poster_path = tvResultWithGenre.tvResultEntity.poster_path,
            overview = tvResultWithGenre.tvResultEntity.overview,
            original_language = tvResultWithGenre.tvResultEntity.original_language,
            backdrop_path = tvResultWithGenre.tvResultEntity.backdrop_path,
            genre_ids = tvResultWithGenre.genreIds.map { genreIds -> genreIds.genre },
            name = tvResultWithGenre.tvResultEntity.name,
            first_air_date = tvResultWithGenre.tvResultEntity.first_air_date,
            original_name = tvResultWithGenre.tvResultEntity.original_name,
            id = tvResultWithGenre.tvResultEntity.id_tv_result
        )
    }

    fun movieDetailToMovieDetailModel(movieDetail: MovieDetail?) =
        movieDetail?.run {
            MovieDetailModel(
                isFavorite = movieDetailResponseEntity.isFavorite,
                id = movieDetailResponseEntity.pk,
                backdrop_path = movieDetailResponseEntity.backdrop_path,
                adult = movieDetailResponseEntity.adult,
                original_language = movieDetailResponseEntity.original_language,
                title = movieDetailResponseEntity.title,
                release_date = movieDetailResponseEntity.release_date,
                overview = movieDetailResponseEntity.overview,
                original_title = movieDetailResponseEntity.original_title,
                poster_path = movieDetailResponseEntity.poster_path,
                vote_count = movieDetailResponseEntity.vote_count,
                popularity = movieDetailResponseEntity.popularity,
                video = movieDetailResponseEntity.video,
                vote_average = movieDetailResponseEntity.vote_average,
                tagLine = movieDetailResponseEntity.tagLine,
                status = movieDetailResponseEntity.status,
                runtime = movieDetailResponseEntity.runtime,
                revenue = movieDetailResponseEntity.revenue,
                imDbId = movieDetailResponseEntity.imDbId,
                homepage = movieDetailResponseEntity.homepage,
                budget = movieDetailResponseEntity.budget,
                genres = genre.map { Genres(it.genre_code, it.name) }
            )
        }

    fun tvDetailToTvDetailModel(tvDetail: TvDetail?) =
        tvDetail?.run {
            TvDetailModel(
                isFavorite = tvDetailResponseEntity.isFavorite,
                id = tvDetailResponseEntity.pk,
                backdrop_path = tvDetailResponseEntity.backdrop_path,
                original_language = tvDetailResponseEntity.original_language,
                overview = tvDetailResponseEntity.overview,
                poster_path = tvDetailResponseEntity.poster_path,
                vote_count = tvDetailResponseEntity.vote_count,
                popularity = tvDetailResponseEntity.popularity,
                vote_average = tvDetailResponseEntity.vote_average,
                status = tvDetailResponseEntity.status,
                homepage = tvDetailResponseEntity.homepage,
                genres = genre.map { Genres(it.genre_code, it.name) },
                name = tvDetailResponseEntity.name,
                first_air_date = tvDetailResponseEntity.first_air_date,
                original_name = tvDetailResponseEntity.original_name,
                in_production = tvDetailResponseEntity.in_production,
                last_air_date = tvDetailResponseEntity.last_air_date,
                number_of_episodes = tvDetailResponseEntity.number_of_episodes,
                number_of_seasons = tvDetailResponseEntity.number_of_seasons,
                type = tvDetailResponseEntity.type
            )
        }

    fun movieResponseToMovieResponseEntity(movieResponse: MovieResponse): MovieResponseEntity {
        return MovieResponseEntity(
            page = movieResponse.page,
            total_results = movieResponse.total_results,
            total_pages = movieResponse.total_pages
        )
    }

    fun movieResultToMovieResultEntity(
        idInsertResponse: Long,
        movieResult: MovieResultModel
    ): MovieResultEntity {
        return MovieResultEntity(
            fk = idInsertResponse,
            pk = movieResult.id,
            popularity = movieResult.popularity,
            vote_count = movieResult.vote_count,
            poster_path = movieResult.poster_path,
            backdrop_path = movieResult.backdrop_path,
            original_language = movieResult.original_language,
            original_title = movieResult.original_title,
            title = movieResult.title,
            vote_average = movieResult.vote_average,
            overview = movieResult.overview,
            release_date = movieResult.release_date
        )
    }

    fun movieDetailResponseToMovieDetailResponseEntity(movieDetailResponse: MovieDetailResponse): MovieDetailResponseEntity {
        return MovieDetailResponseEntity(
            pk = movieDetailResponse.id,
            adult = movieDetailResponse.adult,
            backdrop_path = movieDetailResponse.backdrop_path,
            budget = movieDetailResponse.budget,
            homepage = movieDetailResponse.homepage,
            imDbId = movieDetailResponse.imDbId,
            original_language = movieDetailResponse.original_language,
            title = movieDetailResponse.title,
            release_date = movieDetailResponse.release_date,
            overview = movieDetailResponse.overview,
            vote_average = movieDetailResponse.vote_average,
            original_title = movieDetailResponse.original_title,
            poster_path = movieDetailResponse.poster_path,
            vote_count = movieDetailResponse.vote_count,
            popularity = movieDetailResponse.popularity,
            revenue = movieDetailResponse.revenue,
            runtime = movieDetailResponse.runtime,
            status = movieDetailResponse.status,
            tagLine = movieDetailResponse.tagLine,
            video = movieDetailResponse.video
        )
    }

    fun tvResponseToTvResponseEntity(tvResponse: TvResponse): TvResponseEntity {
        return TvResponseEntity(
            page = tvResponse.page,
            total_results = tvResponse.total_results,
            total_pages = tvResponse.total_pages
        )
    }

    fun tvResultToTvResultEntity(
        idInsertResponse: Long,
        tvResultModel: TvResultModel
    ): TvResultEntity {
        return TvResultEntity(
            id_tv_result_foreign = idInsertResponse,
            id_tv_result = tvResultModel.id,
            popularity = tvResultModel.popularity,
            vote_count = tvResultModel.vote_count,
            poster_path = tvResultModel.poster_path,
            backdrop_path = tvResultModel.backdrop_path,
            original_language = tvResultModel.original_language,
            vote_average = tvResultModel.vote_average,
            overview = tvResultModel.overview,
            original_name = tvResultModel.name,
            first_air_date = tvResultModel.first_air_date,
            name = tvResultModel.name
        )
    }

    fun tvDetailResponseToTvDetailEntity(tvDetailResponse: TvDetailResponse): TvDetailResponseEntity {
        return TvDetailResponseEntity(
            pk = tvDetailResponse.id,
            backdrop_path = tvDetailResponse.backdrop_path,
            homepage = tvDetailResponse.homepage,
            original_language = tvDetailResponse.original_language,
            overview = tvDetailResponse.overview,
            vote_average = tvDetailResponse.vote_average,
            poster_path = tvDetailResponse.poster_path,
            vote_count = tvDetailResponse.vote_count,
            popularity = tvDetailResponse.popularity,
            status = tvDetailResponse.status,
            type = tvDetailResponse.type,
            number_of_seasons = tvDetailResponse.number_of_seasons,
            number_of_episodes = tvDetailResponse.number_of_episodes,
            last_air_date = tvDetailResponse.last_air_date,
            in_production = tvDetailResponse.in_production,
            original_name = tvDetailResponse.original_name,
            first_air_date = tvDetailResponse.first_air_date,
            name = tvDetailResponse.name,
        )
    }
}