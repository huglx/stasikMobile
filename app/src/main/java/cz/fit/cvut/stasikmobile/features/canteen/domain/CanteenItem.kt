package cz.fit.cvut.stasikmobile.features.canteen.domain

data class CanteenItem(
    val title: String,
    val price: String,
    val type: Type,
    val image: String?
)

data class Type(
    val name: String,
    val color: String,
)