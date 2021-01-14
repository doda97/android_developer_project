package android.developer.project.base

import android.developer.project.R
import android.developer.project.data.model.ui.MessageDialogModel
import android.developer.project.network.MissingInternetConnectionException
import android.developer.project.ui.utils.SingleEvent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel: ViewModel(){

    val progressBar: MutableLiveData<SingleEvent<Boolean>> by lazy {
        MutableLiveData<SingleEvent<Boolean>>()
    }

    val globalDialogMessage: MutableLiveData<SingleEvent<MessageDialogModel>> by lazy {
        MutableLiveData<SingleEvent<MessageDialogModel>>()
    }

    val clickEvents: MutableLiveData<SingleEvent<View>> by lazy {
        MutableLiveData<SingleEvent<View>>()
    }

    fun handleError(throwable: Throwable, customMessage: String? = null) {
        throwable.printStackTrace()

        if (throwable is MissingInternetConnectionException ||
            throwable is UnknownHostException ||
            throwable is SocketTimeoutException
        ) {
            Log.e("BaseViewModel", "Missing internet connection", throwable)
            globalDialogMessage.value = SingleEvent(MessageDialogModel.default(R.string.network_error_unknown))
        } else if(throwable is HttpException && throwable.code() == HttpURLConnection.HTTP_UNAVAILABLE){
            globalDialogMessage.value = SingleEvent(MessageDialogModel.default(message = R.string.service_unavailable, positiveText = R.string.cancel))
        } else {
            customMessage?.let {
                globalDialogMessage.value = SingleEvent(MessageDialogModel.default(it))
            } ?: run {
                globalDialogMessage.value = SingleEvent(MessageDialogModel.default(throwable))
            }
        }
    }

    fun showProgress(show: Boolean) {
        progressBar.value = SingleEvent(show)
    }

    fun handleSingleClick(view: View) {
        clickEvents.value = SingleEvent(view)
    }
}