package cz.fit.cvut.stasikmobile.features.canteen.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "canteen")
data class CanteenDB(
    @PrimaryKey val id: String,
    val title: String,
    val price: String,
    val image: String?
)