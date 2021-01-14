package android.developer.project.ui.main.fragment.list

import android.developer.project.data.model.ui.Repository

class RepositoryItemViewModel(val repository: Repository, private val listener: (Repository) -> Unit, private val avatarListener: (Repository) -> Unit) {

    fun click() {
        listener.invoke(repository)
    }

    fun avatarClicked() {
        avatarListener.invoke(repository)
    }
}