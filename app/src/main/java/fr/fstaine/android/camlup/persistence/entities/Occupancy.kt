package fr.fstaine.android.camlup.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class Occupancy(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Instant,
    @ColumnInfo(name = "occupancy") val occupancy: Int,
    @ColumnInfo(name = "hall") val hall: String
)
