package fr.fstaine.android.camlup.model.persistence

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.fstaine.android.camlup.model.persistence.entities.Hall
import fr.fstaine.android.camlup.model.persistence.entities.Occupancy
import fr.fstaine.android.camlup.model.persistence.entities.OccupancyDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import java.time.Duration
import java.time.Instant

@Database(entities = [Occupancy::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class OccupancyRoomDatabase : RoomDatabase() {

    abstract fun occupancyDao(): OccupancyDao

    companion object {

        private val TAG = "OccupancyRoomDatabase"

        @Volatile
        private var INSTANCE: OccupancyRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): OccupancyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OccupancyRoomDatabase::class.java,
                    "occupancy_database"
                ).addCallback(OccupancyDatabaseCallback(scope)
                ).fallbackToDestructiveMigration(
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class OccupancyDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
        }

        suspend fun populateDatabase(occupancyDao: OccupancyDao) {
            occupancyDao.deleteAll()

            occupancyDao.insertAll(
                Occupancy(Instant.now() - Duration.ofMinutes(60), 90, Hall.GERLAND),
                Occupancy(Instant.now() - Duration.ofMinutes(50), 80, Hall.GERLAND),
                Occupancy(Instant.now() - Duration.ofMinutes(40), 30, Hall.GERLAND),
                Occupancy(Instant.now() - Duration.ofMinutes(30), 50, Hall.GERLAND),
                Occupancy(Instant.now() - Duration.ofMinutes(20), 20, Hall.GERLAND),
                Occupancy(Instant.now() - Duration.ofMinutes(10), 10, Hall.GERLAND),
                Occupancy(Instant.now() + Duration.ofMinutes(0 ), 10, Hall.GERLAND),
                Occupancy(Instant.now() + Duration.ofMinutes(10), 10, Hall.GERLAND),
                Occupancy(Instant.now() + Duration.ofMinutes(20), 20, Hall.GERLAND),
                Occupancy(Instant.now() + Duration.ofMinutes(30), 30, Hall.GERLAND),
            )

            occupancyDao.getAll().collect {
                Log.i(TAG, "populateDatabase: $it")
            }
        }
    }
}
