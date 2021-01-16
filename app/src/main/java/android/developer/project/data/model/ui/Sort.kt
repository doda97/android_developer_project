package android.developer.project.data.model.ui

enum class Sort {

    NONE, WatchersASC, WatchersDESC, ForksASC, ForksDESC, UpdatedASC, UpdatedDESC, RepositoryASC, RepositoryDESC;

    override fun toString(): String {
        return when (this) {
            WatchersASC -> "Watcher ASC"
            WatchersDESC -> "Watcher DESC"
            ForksASC -> "Forks ASC"
            ForksDESC -> "Forks DESC"
            UpdatedASC -> "Updated ASC"
            UpdatedDESC -> "Updated DESC"
            RepositoryASC -> "Repository ASC"
            RepositoryDESC -> "Repository DESC"
            NONE -> ""
        }
    }
}

class SortConverter {
    companion object {
        fun toSort(value: String?): Sort {
            return when (value) {
                "Watcher ASC" -> Sort.WatchersASC
                "Watcher DESC" -> Sort.WatchersDESC
                "Forks ASC" -> Sort.ForksASC
                "Forks DESC" -> Sort.ForksDESC
                "Updated ASC" -> Sort.UpdatedASC
                "Updated DESC" -> Sort.UpdatedDESC
                "Repository ASC" -> Sort.RepositoryASC
                "Repository DESC" -> Sort.RepositoryDESC
                else -> Sort.NONE
            }
        }
    }
}