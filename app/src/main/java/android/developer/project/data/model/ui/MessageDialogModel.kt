package android.developer.project.data.model.ui

import android.developer.project.R

class MessageDialogModel private constructor(
    val message: Int? = null,
    val localizedMessage: String? = null,
    val title: Int? = null,
    val positiveText: Int?,
    val negativeText: Int?,
    val type: MessageDialogType
) {

    companion object {
        fun default(title: Int? = R.string.error, positiveText: Int? = R.string.ok, negativeText: Int? = null) = MessageDialogModel(message = R.string.unknown_error, title = title, positiveText = positiveText, negativeText = negativeText, type = MessageDialogType.DEFAULT)
        fun default(message: String, title: Int? = R.string.error, positiveText: Int? = R.string.ok, negativeText: Int? = null) = MessageDialogModel(localizedMessage = message, title = title, positiveText = positiveText, negativeText = negativeText, type = MessageDialogType.DEFAULT)
        fun default(message: Int, title: Int? = R.string.error, positiveText: Int? = R.string.ok, negativeText: Int? = null) = MessageDialogModel(message, title = title, positiveText = positiveText, negativeText = negativeText, type = MessageDialogType.DEFAULT)
        fun default(throwable: Throwable?, title: Int? = R.string.error, positiveText: Int? = R.string.ok, negativeText: Int? = null) = MessageDialogModel(localizedMessage = throwable?.message ?: "Unknown error occurred!", title = title, positiveText = positiveText, negativeText = negativeText, type = MessageDialogType.DEFAULT)

        fun withResult(message: String, title: Int? = null, positiveText: Int? = null, negativeText: Int? = null) = MessageDialogModel(localizedMessage = message, title = title, positiveText = positiveText, negativeText = negativeText, type = MessageDialogType.WITH_RESULT)
        fun withResult(message: Int, title: Int? = null, positiveText: Int? = null, negativeText: Int? = null) = MessageDialogModel(message = message, title = title, positiveText = positiveText, negativeText = negativeText, type = MessageDialogType.WITH_RESULT)
    }
}

enum class MessageDialogType {
    DEFAULT, // normal message dialog
    WITH_RESULT // show message dialog but handle result by setting target fragment
}