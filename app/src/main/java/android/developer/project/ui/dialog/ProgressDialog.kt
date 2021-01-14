package android.developer.project.ui.dialog

import android.developer.project.R
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class ProgressDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_blocking_progress, container, false)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {

            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
            dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//            setStatusBarColor()
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setOnCancelListener {
                activity?.onBackPressed()
            }
        }
    }

    companion object {

        private const val TAG = "ProgressDialog"

        private fun newInstance(): ProgressDialog {
            val args = Bundle()

            val fragment = ProgressDialog()
            fragment.arguments = args
            return fragment
        }

        fun show(fragmentManager: FragmentManager) {
            newInstance().show(fragmentManager, TAG)
        }

        fun hide(fragmentManager: FragmentManager) {
            val dialogFragment = fragmentManager.findFragmentByTag(TAG) as DialogFragment?
            dialogFragment?.dismissAllowingStateLoss()
        }
    }
}