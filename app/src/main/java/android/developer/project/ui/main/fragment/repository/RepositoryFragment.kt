package android.developer.project.ui.main.fragment.repository

import android.content.Intent
import android.developer.project.R
import android.developer.project.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.developer.project.BR
import android.developer.project.databinding.FragmentRepositoryBinding
import android.developer.project.ui.main.fragment.profile.ProfileFragmentDirections
import android.net.Uri

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RepositoryFragment : BaseFragment<FragmentRepositoryBinding, RepositoryViewModel>() {

    private val repositoryViewModel: RepositoryViewModel by viewModels()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_repository

    override fun getMViewModel(): RepositoryViewModel = repositoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = requireArguments().getString(EXTRA_REPOSITORY_USERNAME, "")
        val repositoryName = requireArguments().getString(EXTRA_REPOSITORY_NAME, "")

        repositoryViewModel.clickEvents.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { view ->
                when (view.id) {
                    R.id.user_name -> {
                        repositoryViewModel.getUserUrl()?.let { url ->
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(browserIntent)
                        }
                    }
                    R.id.repository_name -> {
                        repositoryViewModel.getRepositoryUrl()?.let { url ->
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(browserIntent)
                        }
                    }
                    else -> {
                    }
                }
            }
        })

        repositoryViewModel.loadRepository(username, repositoryName)
    }

    companion object {
        private const val EXTRA_REPOSITORY_USERNAME = "repository_username"
        private const val EXTRA_REPOSITORY_NAME = "repository_name"
    }
}