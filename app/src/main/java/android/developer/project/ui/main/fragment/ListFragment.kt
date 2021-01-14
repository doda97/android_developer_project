package android.developer.project.ui.main.fragment

import android.developer.project.R
import android.developer.project.base.BaseFragment
import android.developer.project.databinding.FragmentLoginBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.developer.project.BR

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentLoginBinding, ListViewModel>() {

    private val listViewModel: ListViewModel by viewModels()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_list

    override fun getMViewModel(): ListViewModel = listViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel.loadRepositories()
    }
}