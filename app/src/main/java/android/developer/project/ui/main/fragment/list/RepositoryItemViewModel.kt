package android.developer.project.ui.main.fragment.list

import android.developer.project.data.model.ui.Repository
import androidx.lifecycle.MutableLiveData

class RepositoryItemViewModel(val repository: Repository, private val listener: (Repository) -> Unit, private val avatarListener: (Repository) -> Unit) {

    val watchers: MutableLiveData<String> = MutableLiveData("0")
    val forks: MutableLiveData<String> = MutableLiveData("0")
    val issues: MutableLiveData<String> = MutableLiveData("0")

    fun click() {
        listener.invoke(repository)
    }

    fun avatarClicked() {
        avatarListener.invoke(repository)
    }
}