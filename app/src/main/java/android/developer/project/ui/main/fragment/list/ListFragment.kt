package android.developer.project.ui.main.fragment.list

import android.content.Intent
import android.developer.project.BR
import android.developer.project.R
import android.developer.project.base.BaseFragment
import android.developer.project.data.model.ui.Sort
import android.developer.project.databinding.FragmentListBinding
import android.developer.project.ui.dialog.sort.RepositorySortDialog.Companion.EXTRA_SORT
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        // Repositories
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

        // Sort
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Sort>(EXTRA_SORT)?.observe(viewLifecycleOwner) { result ->
            listViewModel.selectedSort.value = result
        }

        listViewModel.selectedSort.observe(viewLifecycleOwner) {
            listViewModel.sortList()
        }

        setHasOptionsMenu(true)

        // Search
        listViewModel.searchText.observe(viewLifecycleOwner) {
            listViewModel.loadRepositories()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.sort -> {
                val action = ListFragmentDirections.actionListToSort()
                navigate(action)
            }
        }
        return true
    }
}