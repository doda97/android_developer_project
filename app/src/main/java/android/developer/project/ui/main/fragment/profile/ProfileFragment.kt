package android.developer.project.ui.main.fragment.profile

import android.content.Intent
import android.developer.project.BR
import android.developer.project.R
import android.developer.project.base.BaseFragment
import android.developer.project.databinding.FragmentProfileBinding
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun getMViewModel(): ProfileViewModel = profileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId: String? = requireArguments().getString(EXTRA_REPOSITORY_AUTHOR)

        userId?.let {
            profileViewModel.loadProfile(it)
        } ?: kotlin.run {
            logout.visibility = VISIBLE
        }

        profileViewModel.clickEvents.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { view ->
                when (view.id) {
                    R.id.logout -> {
                        profileViewModel.logout()
                        val action = ProfileFragmentDirections.actionProfileToLogin()
                        navigate(action)
                        requireActivity().finish()
                    }
                    R.id.git_account -> {
                        profileViewModel.getGithubUrl()?.let { url ->
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(browserIntent)
                        }
                    }
                    else -> {
                    }
                }
            }
        })
    }

    companion object {
        private const val EXTRA_REPOSITORY_AUTHOR = "repository_owner"
    }
}