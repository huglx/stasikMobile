package cz.fit.cvut.stasikmobile.features.profile.domain

data class LoginResponse(
    val users: List<String>,
    val isSuccess: Boolean
)