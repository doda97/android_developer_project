package android.developer.project.ui.main.fragment.repository

import android.developer.project.R
import android.developer.project.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.developer.project.BR
import android.developer.project.databinding.FragmentRepositoryBinding

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RepositoryFragment : BaseFragment<FragmentRepositoryBinding, RepositoryViewModel>() {

    private val repositoryViewModel: RepositoryViewModel by viewModels()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_repository

    override fun getMViewModel(): RepositoryViewModel = repositoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repositoryViewModel.loadRepositories()
    }
}