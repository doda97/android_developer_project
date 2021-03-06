package android.developer.project.data.model.ui

import org.threeten.bp.LocalDateTime

data class Repository(
    val id: Long,
    val authorImage: String,
    val authorName: String,
    val repositoryName: String,
    val watchers: Long,
    val forks: Long,
    val issues: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val defaultBranch: String,
    val userType: String,
    val repositoryUrl: String,
    val authorUrl: String,
    val language: String?
) {
    fun getString(): String {
        return this.toString()
    }
}
