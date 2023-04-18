package cz.fit.cvut.stasikmobile.features.canteen.data.api

import kotlinx.serialization.Serializable

@Serializable
data class CanteenItemApi(
    val title: String,
    val price: String,
    val type: Type,
    val image: String? = null
)
@Serializable
data class Type(
    val name: String,
    val color: String,
)