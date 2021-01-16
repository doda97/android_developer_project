package android.developer.project.data.model.rest

data class RepositorySearchResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<RepositoryResponse>
)
