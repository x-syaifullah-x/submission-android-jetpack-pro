package id.xxx.submission.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.nhaarman.mockitokotlin2.verify
import id.xxx.submission.data.Resource
import id.xxx.submission.data.source.local.LocalTvDataSource
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetail
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetailGenreEntity
import id.xxx.submission.data.source.local.entity.detail.tv.TvDetailResponseEntity
import id.xxx.submission.data.source.local.entity.discover.tv.TvResultWithGenre
import id.xxx.submission.data.source.remote.RemoteDataSource
import id.xxx.submission.utils.Executors
import id.xxx.submission.utils.LiveDataTestUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvRepositoryTest {
    private lateinit var repository: TvRepositoryFake

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var executors: Executors

    @Mock
    private lateinit var local: LocalTvDataSource

    @Mock
    private lateinit var remote: RemoteDataSource

    @Mock
    lateinit var dataSourceFactory: DataSource.Factory<Int, TvResultWithGenre>

    @Before
    fun setUp() {
        repository = TvRepositoryFake(executors, local, remote)
    }

    @Test
    fun get_result() {
        val simpleSQLiteQuery = SimpleSQLiteQuery("")

        Mockito.`when`(local.getResultRawQuery(simpleSQLiteQuery)).thenReturn(dataSourceFactory)

        val value = LiveDataTestUtil.getValue(repository.getResult(simpleSQLiteQuery))

        verify(local).getResultRawQuery(simpleSQLiteQuery)
        Assert.assertNotNull(value)
    }

    @Test
    fun get_detail() {
        val pk = 100
        val entity = TvDetailResponseEntity(pk)
        val genre = listOf(TvDetailGenreEntity(102, 19, 10, "test"))

        Mockito.`when`(local.getDetail(pk)).thenReturn(MutableLiveData(TvDetail(entity, genre)))

        val resource = LiveDataTestUtil.getValue(repository.getDetail(pk))
        verify(local).getDetail(pk)
        Assert.assertTrue(resource is Resource.Success)
        when (resource) {
            is Resource.Success -> {
                Assert.assertEquals(pk, resource.data.id)
                Assert.assertEquals(genre[0].genre_code, resource.data.genres[0].genre_code)
                Assert.assertEquals(genre[0].name, resource.data.genres[0].name)
            }
        }
    }

    @Test
    fun get_favorite() {
        val simpleSQLiteQuery = SimpleSQLiteQuery("")

        Mockito.`when`(local.getFavorite(simpleSQLiteQuery)).thenReturn(dataSourceFactory)

        val value = LiveDataTestUtil.getValue(repository.getFavorite(simpleSQLiteQuery))

        verify(local).getFavorite(simpleSQLiteQuery)
        Assert.assertNotNull(value)
    }
}