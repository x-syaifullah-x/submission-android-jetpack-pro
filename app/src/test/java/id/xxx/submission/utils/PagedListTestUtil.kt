package id.xxx.submission.utils

import androidx.paging.PagedList
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

object PagedListTestUtil {

    @Suppress("UNCHECKED_CAST")
    fun <T> mockPagedList(list: List<T>): PagedList<T> {

        val pagedList = mock(PagedList::class.java) as PagedList<T>

        `when`(pagedList[anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        `when`(pagedList.snapshot()).thenReturn(list)
        `when`(pagedList.size).thenReturn(list.size)

        return pagedList
    }
}