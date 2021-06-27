package fr.fstaine.android.camlup.persistence

import androidx.room.TypeConverter
import fr.fstaine.android.camlup.persistence.entities.Hall
import java.time.Instant

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? = value?.let(Instant::ofEpochMilli)

    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? = date?.toEpochMilli()

    @TypeConverter
    fun fromHall(value: Hall?): String? = value?.name

    @TypeConverter
    fun nameToHall(name: String?): Hall? = name?.let(Hall::valueOf)
}
