package android.developer.project.base

import android.developer.project.R
import android.developer.project.data.model.ui.MessageDialogType
import android.developer.project.ui.dialog.MessageDialog
import android.developer.project.ui.dialog.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel>: Fragment() {

    companion object {
        const val MESSAGE_DIALOG_REQUEST_CODE = 103
    }

    private lateinit var mRootView: View
    private lateinit var mViewDataBinding: T
    private lateinit var mViewModel: V

    val navigationOptionsBuilder = NavOptions.Builder()
        .setEnterAnim(R.anim.right_to_left_enter)
        .setExitAnim(R.anim.right_to_left_exit)
        .setPopEnterAnim(R.anim.left_to_right_enter)
        .setPopExitAnim(R.anim.left_to_right_exit)

    abstract fun getBindingVariable(): Int

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getMViewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getMViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = mViewDataBinding.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner
        mViewDataBinding.executePendingBindings()

        mViewModel.globalDialogMessage.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { message ->
                when (message.type) {
                    MessageDialogType.WITH_RESULT -> MessageDialog.show(
                        parentFragmentManager,
                        message,
                        this,
                        MESSAGE_DIALOG_REQUEST_CODE
                    )
                    else -> MessageDialog.show(parentFragmentManager, message)
                }
            }
        })

        mViewModel.progressBar.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { show ->
                if (show) ProgressDialog.show(parentFragmentManager)
                else ProgressDialog.hide(parentFragmentManager)
            }
        })
    }

    fun navigate(destination: NavDirections, navOptions: NavOptions.Builder = navigationOptionsBuilder) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
                ?.let { navigate(destination, navOptions.build()) }
    }
}