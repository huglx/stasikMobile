package cz.fit.cvut.stasikmobile.features.home.data.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserApi(
    val username: String,
    val tt: List<Subject>,
    @SerialName("lastUpdated")
    val lastUpdated: Long,
    val blacklist: List<String>
)

@Serializable
data class Subject(
    val id: Long,
    val name: String?,
    val starts_at: String,
    val ends_at: String,
    val deleted: Boolean,
    val capacity: Int,
    val occupied: Int,
    val event_type: String,
    val parallel: String,
    val links: Links
)

@Serializable
data class Links(
    val course: String,
    val room: String,
    val teachers: List<String>
)