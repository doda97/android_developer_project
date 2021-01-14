package android.developer.project.data.model.ui

data class Repository(
    val id: Long,
    val authorImage: String,
    val authorName: String,
    val repositoryName: String,
    val watchers: String,
    val forks: String,
    val issues: String
)
