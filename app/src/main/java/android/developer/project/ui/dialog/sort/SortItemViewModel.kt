package android.developer.project.ui.dialog.sort


class SortItemViewModel(val sort: String, private val listener: (String) -> Unit) {

    fun click() {
        listener.invoke(sort)
    }
}