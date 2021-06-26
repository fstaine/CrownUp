package fr.fstaine.android.camlup

import android.app.Application
import fr.fstaine.android.camlup.persistence.OccupancyRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CrowdUpApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { OccupancyRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { OccupancyRepository(database.occupancyDao()) }
}
