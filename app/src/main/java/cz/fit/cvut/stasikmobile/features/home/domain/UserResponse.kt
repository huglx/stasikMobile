package cz.fit.cvut.stasikmobile.features.home.domain

data class UserResponse (
    val users: List<List<User>>,
    val isSuccess: Boolean = false,
)