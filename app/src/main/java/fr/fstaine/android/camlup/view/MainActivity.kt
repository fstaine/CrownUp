package fr.fstaine.android.camlup.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.fstaine.android.camlup.CrowdUpApplication
import fr.fstaine.android.camlup.OccupancyViewModel
import fr.fstaine.android.camlup.OccupancyViewModelFactory
import fr.fstaine.android.camlup.R

class MainActivity : AppCompatActivity() {

    private val occupancyViewModel: OccupancyViewModel by viewModels {
        OccupancyViewModelFactory((application as CrowdUpApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = OccupancyListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        occupancyViewModel.allOccupancies.observe(this, Observer { occupancies ->
            occupancies?.let { adapter.submitList(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            occupancyViewModel.fetchCurrentOccupancy()
        }
    }
}
