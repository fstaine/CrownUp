package fr.fstaine.android.camlup

import android.app.Application
import fr.fstaine.android.camlup.model.background.AutoBackgroundUpdater
import fr.fstaine.android.camlup.model.net.ClimbUpOccupancyService
import fr.fstaine.android.camlup.model.persistence.OccupancyRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CrowdUpApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { OccupancyRoomDatabase.getDatabase(this, applicationScope) }
    val occupancyService by lazy { ClimbUpOccupancyService() }
    val repository by lazy { OccupancyRepository(database.occupancyDao(), occupancyService, applicationScope) }
    val backgroundUpdater by lazy { AutoBackgroundUpdater(this, repository) }
}
