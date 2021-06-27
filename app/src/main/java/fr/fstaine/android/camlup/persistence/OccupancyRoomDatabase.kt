package fr.fstaine.android.camlup.persistence

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.fstaine.android.camlup.persistence.entities.Occupancy
import fr.fstaine.android.camlup.persistence.entities.OccupancyDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.Period
import java.time.temporal.TemporalAmount

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
                val instace = Room.databaseBuilder(
                    context.applicationContext,
                    OccupancyRoomDatabase::class.java,
                    "occupancy_datase"
                ).addCallback(OccupancyDatabaseCallback(scope)
                ).fallbackToDestructiveMigration(
                ).build()
                INSTANCE = instace
                return instace
            }
        }
    }

    private class OccupancyDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.occupancyDao())
                }
            }
        }

        suspend fun populateDatabase(occupancyDao: OccupancyDao) {
            occupancyDao.deleteAll()

            occupancyDao.insertAll(
                Occupancy(1 , Instant.now() - Duration.ofMinutes(60), 90, "Gerland"),
                Occupancy(2 , Instant.now() - Duration.ofMinutes(50), 80, "Gerland"),
                Occupancy(3 , Instant.now() - Duration.ofMinutes(40), 30, "Gerland"),
                Occupancy(4 , Instant.now() - Duration.ofMinutes(30), 50, "Gerland"),
                Occupancy(5 , Instant.now() - Duration.ofMinutes(20), 20, "Gerland"),
                Occupancy(6 , Instant.now() - Duration.ofMinutes(10), 10, "Gerland"),
                Occupancy(7 , Instant.now() + Duration.ofMinutes(0 ), 10, "Gerland"),
                Occupancy(8 , Instant.now() + Duration.ofMinutes(10), 10, "Gerland"),
                Occupancy(9 , Instant.now() + Duration.ofMinutes(20), 20, "Gerland"),
                Occupancy(10, Instant.now() + Duration.ofMinutes(30), 30, "Gerland"),
            )

            occupancyDao.getAll().collect {
                Log.d(TAG, "populateDatabase: $it")
            }
        }
    }
}
