package fr.fstaine.android.camlup.model.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class Occupancy(
    @ColumnInfo(name = "timestamp") val timestamp: Instant,
    @ColumnInfo(name = "occupancy") val occupancy: Int,
    @ColumnInfo(name = "hall") val hall: Hall
) {
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
}
