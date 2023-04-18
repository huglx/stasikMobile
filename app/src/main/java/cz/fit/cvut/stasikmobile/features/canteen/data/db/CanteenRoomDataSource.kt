package cz.fit.cvut.stasikmobile.features.canteen.data.db

import cz.fit.cvut.stasikmobile.features.canteen.data.CanteenLocalDataSource
import cz.fit.cvut.stasikmobile.features.canteen.domain.CanteenItem
import java.util.*

class CanteenRoomDataSource(private val canteenDao: CanteenDao): CanteenLocalDataSource {
    override suspend fun getCanteen(inx: Int): List<CanteenItem> {
        return canteenDao.getCanteen().map { it.toCanteenItem() }
    }

    private fun CanteenDB.toCanteenItem(): CanteenItem {
        val htmlRegex = "<.*?>"
        return CanteenItem(
            title = title.replace(htmlRegex.toRegex(), ""),
            price = price.replace(Regex(",.*;"), " "),
            image = image,
            type = CanteenItem.Type(
                name = "",
                color = ""
            )
        )
    }

    override suspend fun insert(canteen: List<CanteenItem>) {
        canteenDao.insert(canteen = canteen.map { it.toDb() })
    }

    private fun CanteenItem.toDb(): CanteenDB {
        return CanteenDB(
            title = title,
            price = price,
            image = image,
            id = UUID.randomUUID().toString()
        )
    }

    override suspend fun deleteAll() {
       canteenDao.deleteAll()
    }

}