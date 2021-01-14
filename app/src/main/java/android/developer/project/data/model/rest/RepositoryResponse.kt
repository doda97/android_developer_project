package android.developer.project.data.model.rest

import android.developer.project.data.model.ui.Repository
import org.threeten.bp.LocalDateTime

data class RepositoryResponse (
    val id: Long,
    val name: String,
    val owner: OwnerResponse,
    val forks: Long,
    val open_issues: Long,
    val watchers: Long,
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime,
    val default_branch: String,
    val html_url: String
)

fun RepositoryResponse.toRepository() = Repository(
    id,
    owner.avatar_url,
    owner.login,
    name,
    watchers.toString(),
    forks.toString(),
    open_issues.toString(),
    created_at,
    updated_at,
    default_branch,
    owner.type,
    html_url,
    owner.html_url
)