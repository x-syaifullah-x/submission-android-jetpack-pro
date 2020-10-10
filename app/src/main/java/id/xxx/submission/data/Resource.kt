package id.xxx.submission.data

sealed class Resource<out R> {
    data class Loading<out T>(val data: T? = null) : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Empty<out T>(val data: T? = null) : Resource<T>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
}