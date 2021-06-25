package fr.fstaine.android.camlup.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.fstaine.android.camlup.persistence.entities.Occupancy
import fr.fstaine.android.camlup.persistence.entities.OccupancyDao

@Database(entities = [Occupancy::class], version = 1, exportSchema = false)
abstract class OccupancyRoomDatabase : RoomDatabase() {

    abstract fun occupancyDao(): OccupancyDao

    companion object {
        @Volatile
        private var INSTANCE: OccupancyRoomDatabase? = null

        fun getDatabase(context: Context): OccupancyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instace = Room.databaseBuilder(
                    context.applicationContext,
                    OccupancyRoomDatabase::class.java,
                    "occupancy_datase"
                ).build()
                INSTANCE = instace
                return instace
            }
        }
    }
}
