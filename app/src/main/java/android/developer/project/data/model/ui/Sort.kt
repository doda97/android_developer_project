package android.developer.project.data.model.ui

enum class Sort {

    NONE, WatchersASC, WatchersDESC, ForksASC, ForksDESC, IssuesASC, IssuesDESC, RepositoryASC, RepositoryDESC;

    override fun toString(): String {
        return when (this) {
            WatchersASC -> "Watcher ASC"
            WatchersDESC -> "Watcher DESC"
            ForksASC -> "Forks ASC"
            ForksDESC -> "Forks DESC"
            IssuesASC -> "Issues ASC"
            IssuesDESC -> "Issues DESC"
            RepositoryASC -> "Repository ASC"
            RepositoryDESC -> "Repository DESC"
            NONE -> ""
        }
    }
}



class SortConverter {
    companion object {
        fun toSort(value: String?): Sort? {
            return when (value) {
                "Watcher ASC" -> Sort.WatchersASC
                "Watcher DESC" -> Sort.WatchersDESC
                "Forks ASC" -> Sort.ForksASC
                "Forks DESC" -> Sort.ForksDESC
                "Issues ASC" -> Sort.IssuesASC
                "Issues DESC" -> Sort.IssuesASC
                "Repository ASC" -> Sort.RepositoryASC
                "Repository DESC" -> Sort.RepositoryDESC
                else -> Sort.NONE
            }
        }
    }
}