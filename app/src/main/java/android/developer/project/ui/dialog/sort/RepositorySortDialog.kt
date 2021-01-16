package android.developer.project.ui.dialog.sort

import android.developer.project.BR
import android.developer.project.R
import android.developer.project.base.BaseBottomSheetDialog
import android.developer.project.data.model.ui.Sort
import android.developer.project.data.model.ui.SortConverter
import android.developer.project.data.model.ui.SortConverter.Companion.toSort
import android.developer.project.databinding.DialogRepositorySortBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_repository_sort.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RepositorySortDialog : BaseBottomSheetDialog<DialogRepositorySortBinding, RepositorySortViewModel>() {

    private var sortAdapter: SortAdapter? = null

    private val sortViewModel: RepositorySortViewModel by viewModels()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.dialog_repository_sort

    override fun getViewModel(): RepositorySortViewModel = sortViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sortAdapter = SortAdapter {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(EXTRA_SORT, toSort(it))
            findNavController().navigateUp()
        }

        sort_list.run {
            adapter = sortAdapter
        }

        sortAdapter?.updateData(sortViewModel.getSortList())

        sortViewModel.clickEvents.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { view ->
                when (view.id) {
                    R.id.clear -> {
                        findNavController().previousBackStackEntry?.savedStateHandle?.set(EXTRA_SORT, Sort.NONE)
                        requireActivity().onBackPressed()
                    }
                    else -> {
                    }
                }
            }
        })
    }

    companion object {
        const val EXTRA_SORT = "sort"
    }
}