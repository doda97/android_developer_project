package android.developer.project.data.model.rest

import android.developer.project.data.model.ui.Owner

data class OwnerResponse (
    val avatar_url: String,
    val login: String
    )

fun OwnerResponse.toOwner(): Owner = Owner(
    avatar_url,
    login
)