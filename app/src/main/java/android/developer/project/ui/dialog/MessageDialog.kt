package android.developer.project.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.developer.project.data.model.ui.MessageDialogModel
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MessageDialog: DialogFragment() {

    companion object {

        const val TAG = "MessageDialog"
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        private const val EXTRA_LOCALIZED_MESSAGE = "EXTRA_LOCALIZED_MESSAGE"
        private const val EXTRA_POSITIVE_TEXT = "EXTRA_POSITIVE_TEXT"
        private const val EXTRA_NEGATIVE_TEXT = "EXTRA_NEGATIVE_TEXT"

        private fun newInstance(messageDialogModel: MessageDialogModel, targetFragment: Fragment? = null, targetFragmentRequestCode: Int? = null): MessageDialog {
            val args = Bundle()
            messageDialogModel.title?.let {
                args.putInt(EXTRA_TITLE, it)
            }

            args.putString(EXTRA_LOCALIZED_MESSAGE, messageDialogModel.localizedMessage)

            messageDialogModel.message?.let {
                args.putInt(EXTRA_MESSAGE, it)
            }

            messageDialogModel.positiveText?.let {
                args.putInt(EXTRA_POSITIVE_TEXT, it)
            }

            messageDialogModel.negativeText?.let {
                args.putInt(EXTRA_NEGATIVE_TEXT, it)
            }

            val fragment = MessageDialog()
            fragment.arguments = args
            targetFragmentRequestCode?.let {
                fragment.setTargetFragment(targetFragment, it)
            }
            return fragment
        }

        fun show(fragmentManager: FragmentManager, message: MessageDialogModel, targetFragment: Fragment? = null, targetFragmentRequestCode: Int? = null) {
            newInstance(message, targetFragment, targetFragmentRequestCode).show(fragmentManager, TAG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val titleText = requireArguments().getInt(EXTRA_TITLE, -1)
        val messageText = requireArguments().getInt(EXTRA_MESSAGE, -1)
        val localizedMessageText = requireArguments().getString(EXTRA_LOCALIZED_MESSAGE, null)
        val positiveText = requireArguments().getInt(EXTRA_POSITIVE_TEXT, -1)
        val negativeText = requireArguments().getInt(EXTRA_NEGATIVE_TEXT, -1)
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            if (titleText > -1) {
                builder.setTitle(titleText)
            }

            if (messageText > -1) {
                builder.setMessage(messageText)
            } else if(!localizedMessageText.isNullOrBlank()) {
                builder.setMessage(localizedMessageText)
            }

            if (positiveText > -1) {
                builder.setPositiveButton(positiveText) { dialog, _ ->
                    sendResult(Activity.RESULT_OK)
                    dialog.dismiss()
                }
            }
            if (negativeText > -1) {
                builder.setNegativeButton(negativeText) { dialog, _ ->
                    sendResult(Activity.RESULT_CANCELED)
                    dialog.dismiss()
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun sendResult(result: Int) {
        if(targetFragment != null) {
            targetFragment?.onActivityResult(targetRequestCode, result, Intent())
        }
    }
}