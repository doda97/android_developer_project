package android.developer.project.data.model.rest

import android.developer.project.data.model.ui.Repository

data class RepositoryResponse (
    val id: Long,
    val name: String,
    val owner: OwnerResponse,
    val forks: Long,
    val open_issues: Long,
    val watchers: Long,
)

fun RepositoryResponse.toRepository() = Repository(
    id,
    owner.avatar_url,
    owner.login,
    name,
    watchers.toString(),
    forks.toString(),
    open_issues.toString()
)