package android.developer.project.ui.main.fragment.list

import android.developer.project.R
import android.developer.project.base.BaseFragment
import android.developer.project.databinding.FragmentLoginBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.developer.project.BR
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentLoginBinding, ListViewModel>() {

    private val listViewModel: ListViewModel by viewModels()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_list

    override fun getMViewModel(): ListViewModel = listViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repositoryAdapter = RepositoryAdapter {
            val action = ListFragmentDirections.actionListToRepository(it.id)
            navigate(action)
        }

        repository_list.run {
            setHasFixedSize(true)
            adapter = repositoryAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        listViewModel.repositories.observe(viewLifecycleOwner) {
            (repository_list.adapter as RepositoryAdapter).updateData(it)
        }

        listViewModel.loadRepositories()
    }
}