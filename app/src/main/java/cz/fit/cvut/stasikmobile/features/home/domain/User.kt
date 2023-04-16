package cz.fit.cvut.stasikmobile.features.home.domain

data class User(
    val username: String,

    val subjects: List<Subject>?
) {

    data class Subject(
        val id: String,
        val links: Links,
        val starts_at: String
    ){
        data class Links(
            val course: String
        )
    }
}

