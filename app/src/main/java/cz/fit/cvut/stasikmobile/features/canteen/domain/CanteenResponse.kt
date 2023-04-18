package cz.fit.cvut.stasikmobile.features.canteen.domain

data class CanteenResponse(
    val data: List<CanteenItem>,
    val isSuccess: Boolean
)