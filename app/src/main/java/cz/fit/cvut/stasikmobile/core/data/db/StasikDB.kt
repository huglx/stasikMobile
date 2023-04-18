package cz.fit.cvut.stasikmobile.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.fit.cvut.stasikmobile.features.canteen.data.db.CanteenDB
import cz.fit.cvut.stasikmobile.features.canteen.data.db.CanteenDao

@Database(version = 1, entities = [CanteenDB::class])
abstract class StasikDB: RoomDatabase() {
    abstract fun stasikDB(): CanteenDao

    companion object {
        fun instance(context: Context): StasikDB {
            return Room.databaseBuilder(context, StasikDB::class.java, "stas.db").build()
        }
    }
}