package cz.fit.cvut.stasikmobile.features.canteen.data.api

import cz.fit.cvut.stasikmobile.features.canteen.data.CanteenRemoteDataSource
import cz.fit.cvut.stasikmobile.features.canteen.domain.CanteenItem

class CanteenRetrofitDataSource(private val apiDescription: CanteenApiDescription): CanteenRemoteDataSource {
    override suspend fun getCanteen(inx: Int): List<CanteenItem> {
        return apiDescription.getCanteen(inx).map {
            it.toCanteenItem()
        }
    }

    private fun CanteenItemApi.toCanteenItem(): CanteenItem {
        val htmlRegex = "<.*?>"
        return CanteenItem(
            title = title.replace(htmlRegex.toRegex(), ""),
            price = price.replace(Regex(",.*;"), " "),
            image = image,
            type = CanteenItem.Type(
                name = type.name,
                color = type.color
            )
        )
    }
}