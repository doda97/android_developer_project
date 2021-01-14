package android.developer.project.ui.main.fragment.list

import android.content.Intent
import android.developer.project.BR
import android.developer.project.R
import android.developer.project.base.BaseFragment
import android.developer.project.databinding.FragmentListBinding
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding, ListViewModel>() {

    private val listViewModel: ListViewModel by viewModels()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_list

    override fun getMViewModel(): ListViewModel = listViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repositoryAdapter = RepositoryAdapter({
            val action =
                ListFragmentDirections.actionListToRepository(it.authorName, it.repositoryName)
            navigate(action)
        }, {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.authorUrl))
            startActivity(browserIntent)
        })

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