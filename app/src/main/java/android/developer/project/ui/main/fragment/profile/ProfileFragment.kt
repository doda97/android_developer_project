package android.developer.project.ui.main.fragment.profile

import android.developer.project.R
import android.developer.project.base.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.developer.project.BR
import android.developer.project.databinding.FragmentProfileBinding

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun getMViewModel(): ProfileViewModel = profileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = requireArguments().getString(EXTRA_REPOSITORY_AUTHOR, "")

        profileViewModel.loadProfile(userId)
    }

    companion object {
        private const val EXTRA_REPOSITORY_AUTHOR = "repository_owner"
    }
}