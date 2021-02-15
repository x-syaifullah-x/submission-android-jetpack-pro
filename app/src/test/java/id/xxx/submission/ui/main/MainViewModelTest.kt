package id.xxx.submission.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import id.xxx.submission.data.Resource
import id.xxx.submission.data.Resource.*
import id.xxx.submission.data.model.MovieResultModel
import id.xxx.submission.data.model.TvResultModel
import id.xxx.submission.data.repository.MovieRepository
import id.xxx.submission.data.repository.TvRepository
import id.xxx.submission.utils.DummyData.movieResultModel
import id.xxx.submission.utils.DummyData.tvResultModel
import id.xxx.submission.utils.PagedListTestUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private val tvResourceSuccess = Success(PagedListTestUtil.mockPagedList(tvResultModel()))
    private val movieResourceSuccess = Success(PagedListTestUtil.mockPagedList(movieResultModel()))
    private val resourceError = Error("error", null)
    private val resourceEmpty = Empty(null)

    @Mock
    lateinit var movieRepo: MovieRepository

    @Mock
    lateinit var tvRepo: TvRepository

    @Mock
    lateinit var observerResourceMovie: Observer<Resource<PagedList<MovieResultModel>>>

    @Mock
    lateinit var observerResourceTv: Observer<Resource<PagedList<TvResultModel>>>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(movieRepo, tvRepo)
    }

    @Test
    fun movie_get_result_resource_success() {
        `when`(movieRepo.getResult()).thenReturn(MutableLiveData(movieResourceSuccess))
        verify(movieRepo).getResult()

        MutableLiveData(movieResourceSuccess).observeForever(observerResourceMovie)
        verify(observerResourceMovie).onChanged(movieResourceSuccess)

        Assert.assertEquals(movieResultModel(), movieResourceSuccess.data.snapshot())
    }

    @Test
    fun movie_get_result_resource_error() {
        `when`(movieRepo.getResult()).thenReturn(MutableLiveData(resourceError))
        verify(movieRepo).getResult()

        MutableLiveData(resourceError).observeForever(observerResourceMovie)
        verify(observerResourceMovie).onChanged(resourceError)
    }

    @Test
    fun movie_get_result_resource_empty() {
        `when`(movieRepo.getResult()).thenReturn(MutableLiveData(resourceEmpty))
        verify(movieRepo).getResult()

        MutableLiveData(resourceEmpty).observeForever(observerResourceMovie)
        verify(observerResourceMovie).onChanged(resourceEmpty)
    }

    @Test
    fun tv_get_result_resource_success() {
        `when`(tvRepo.getResult()).thenReturn(MutableLiveData(tvResourceSuccess))
        verify(tvRepo).getResult()

        MutableLiveData(tvResourceSuccess).observeForever(observerResourceTv)
        verify(observerResourceTv).onChanged(tvResourceSuccess)

        Assert.assertEquals(tvResultModel(), tvResourceSuccess.data.snapshot())
    }

    @Test
    fun tv_get_result_resource_error() {
        `when`(tvRepo.getResult()).thenReturn(MutableLiveData(resourceError))
        verify(tvRepo).getResult()

        MutableLiveData(resourceError).observeForever(observerResourceTv)
        verify(observerResourceTv).onChanged(resourceError)
    }

    @Test
    fun tv_get_result_resource_empty() {
        `when`(tvRepo.getResult()).thenReturn(MutableLiveData(resourceEmpty))
        verify(tvRepo).getResult()

        MutableLiveData(resourceEmpty).observeForever(observerResourceTv)
        verify(observerResourceTv).onChanged(resourceEmpty)
    }

    @Test
    fun movie_favorite_get_result_resource_success() {
        `when`(movieRepo.getFavorite()).thenReturn(MutableLiveData(movieResourceSuccess))
        verify(movieRepo).getFavorite()

        MutableLiveData(movieResourceSuccess).observeForever(observerResourceMovie)
        verify(observerResourceMovie).onChanged(movieResourceSuccess)

        Assert.assertEquals(movieResultModel(), movieResourceSuccess.data.snapshot())
    }

    @Test
    fun movie_favorite_get_result_resource_error() {
        `when`(movieRepo.getFavorite()).thenReturn(MutableLiveData(resourceError))
        verify(movieRepo).getFavorite()

        MutableLiveData(resourceError).observeForever(observerResourceMovie)
        verify(observerResourceMovie).onChanged(resourceError)
    }

    @Test
    fun movie_favorite_get_result_resource_empty() {
        `when`(movieRepo.getFavorite()).thenReturn(MutableLiveData(resourceEmpty))
        verify(movieRepo).getFavorite()

        MutableLiveData(resourceEmpty).observeForever(observerResourceMovie)
        verify(observerResourceMovie).onChanged(resourceEmpty)
    }


    @Test
    fun tv_favorite_get_result_resource_success() {
        `when`(tvRepo.getFavorite()).thenReturn(MutableLiveData(tvResourceSuccess))
        verify(tvRepo).getFavorite()

        MutableLiveData(tvResourceSuccess).observeForever(observerResourceTv)
        verify(observerResourceTv).onChanged(tvResourceSuccess)

        Assert.assertEquals(tvResultModel(), tvResourceSuccess.data.snapshot())
    }

    @Test
    fun tv_favorite_get_result_resource_error() {
        `when`(tvRepo.getFavorite()).thenReturn(MutableLiveData(resourceError))
        verify(tvRepo).getFavorite()

        MutableLiveData(resourceError).observeForever(observerResourceTv)
        verify(observerResourceTv).onChanged(resourceError)
    }

    @Test
    fun tv_favorite_get_result_resource_empty() {
        `when`(tvRepo.getFavorite()).thenReturn(MutableLiveData(resourceEmpty))
        verify(tvRepo).getFavorite()

        MutableLiveData(resourceEmpty).observeForever(observerResourceTv)
        verify(observerResourceTv).onChanged(resourceEmpty)
    }
}