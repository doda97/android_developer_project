package android.developer.project.ui.login.fragment

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
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginFragmentViewModel>() {

    private val loginViewModel: LoginFragmentViewModel by viewModels()

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun getMViewModel(): LoginFragmentViewModel = loginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.userLogin.observe(viewLifecycleOwner, {
            val action = LoginFragmentDirections.actionNavigationLoginToNavigationMain()
            navigate(action, navigationOptionsBuilder)
            requireActivity().finish()
        })

        loginViewModel.clickEvents.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { view ->
                when (view.id) {
                    R.id.submit -> {
                        loginViewModel.login()
                    }
                    else -> {
                    }
                }
            }
        })
    }
}