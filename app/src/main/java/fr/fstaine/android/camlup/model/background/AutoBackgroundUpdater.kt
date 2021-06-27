package fr.fstaine.android.camlup.model.background

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import fr.fstaine.android.camlup.CrowdUpApplication
import fr.fstaine.android.camlup.OccupancyRepository
import kotlinx.coroutines.launch

class AutoBackgroundUpdater (
    private val context: Context,
    private val occupancyRepo: OccupancyRepository
) {

    private val TAG = "AutoBackgroundUpdater"

    init {
        setupAutoWakeup()
    }

    fun setupAutoWakeup() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
        if (alarmIntent != null) {
            alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                alarmIntent
            )
            Log.d(TAG, "Alarm set up !")
        }
    }

    class AlarmReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val app = context.applicationContext as CrowdUpApplication
            app.applicationScope.launch {
                app.repository.fetchCurrentOccupancy()
            }
        }
    }

    class BootReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "android.intent.action.BOOT_COMPLETED") {
                val app = context.applicationContext as CrowdUpApplication
                app.backgroundUpdater.setupAutoWakeup()
            }
        }
    }
}
