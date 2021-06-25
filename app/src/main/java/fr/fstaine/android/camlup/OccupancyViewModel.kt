package fr.fstaine.android.camlup

import androidx.lifecycle.*
import fr.fstaine.android.camlup.persistence.entities.Occupancy
import kotlinx.coroutines.launch

class OccupancyViewModel(private val repository: OccupancyRepository) : ViewModel() {

    val allOccupancies: LiveData<List<Occupancy>> = repository.allOccupancies.asLiveData()

    fun insert(occupancy: Occupancy) = viewModelScope.launch {
        repository.insert(occupancy)
    }
}

class OccupancyViewModelFactory(private val repository: OccupancyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OccupancyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OccupancyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
