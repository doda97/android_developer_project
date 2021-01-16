package android.developer.project.ui.dialog.sort

import android.developer.project.base.BaseViewModel
import android.developer.project.data.model.ui.Sort
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class RepositorySortViewModel @ViewModelInject constructor(@Assisted private val savedStateHandle: SavedStateHandle) :
    BaseViewModel() {
    
    fun getSortList(): List<String> {
        return listOf(
            Sort.WatchersASC.toString(),
            Sort.WatchersDESC.toString(),
            Sort.ForksASC.toString(),
            Sort.ForksDESC.toString(),
            Sort.UpdatedASC.toString(),
            Sort.UpdatedDESC.toString(),
            Sort.RepositoryASC.toString(),
            Sort.RepositoryDESC.toString()
        )
    }
}