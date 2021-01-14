package android.developer.project.data

import retrofit2.HttpException
import java.lang.Exception

sealed class DataState<out R> {
    data class Success<out T>(val data: T): DataState<T>()
    data class Error(val exception: Exception): DataState<Nothing>() {
        val statusCode: Int
            get() {
                if(exception is HttpException){
                    return exception.response()?.code() ?: -1
                }
                return -1
            }
    }
    object Loading: DataState<Nothing>()
}