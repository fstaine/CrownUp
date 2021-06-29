package fr.fstaine.android.camlup.ui.home

import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.fstaine.android.camlup.CrowdUpApplication
import fr.fstaine.android.camlup.OccupancyViewModel
import fr.fstaine.android.camlup.OccupancyViewModelFactory
import fr.fstaine.android.camlup.R
import fr.fstaine.android.camlup.databinding.FragmentHomeBinding
import fr.fstaine.android.camlup.model.persistence.entities.Hall
import fr.fstaine.android.camlup.model.persistence.entities.Occupancy
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var occupancyViewModel: OccupancyViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        occupancyViewModel = ViewModelProvider(
            this,
            OccupancyViewModelFactory((context?.applicationContext as CrowdUpApplication).repository)
        ).get(OccupancyViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dateFormatter = DateFormat.getDateFormat(context)
        val timeFormatter = DateFormat.getTimeFormat(context)

        val refreshButton: Button = binding.refreshButton
        refreshButton.setOnClickListener {
            occupancyViewModel.fetchCurrentOccupancy()
        }
        val textOccupancyGerland: TextView = binding.textOccupancyGerland
        val textUpdatedAtGerland: TextView = binding.textUpdatedAtGerland
        val textOccupancyConfluence: TextView = binding.textOccupancyConfluence
        val textUpdatedAtConfluence: TextView = binding.textUpdatedAtConfluence
        occupancyViewModel.lastOccupancies.observe(viewLifecycleOwner, Observer { occupancies ->
            occupancies[Hall.GERLAND]?.let {
                val date = Date(it.timestamp.toEpochMilli())
                textOccupancyGerland.setTextColor(colorFromOccupancy(it))
                textOccupancyGerland.text =
                    getString(R.string.occupancy_percentage_text, it.occupancy)
                textUpdatedAtGerland.text = getString(
                    R.string.occupancy_timestamp_text,
                    dateFormatter.format(date),
                    timeFormatter.format(date)
                )
            }
            occupancies[Hall.CONFLUENCE]?.let {
                val date = Date(it.timestamp.toEpochMilli())
                textOccupancyConfluence.setTextColor(colorFromOccupancy(it))
                textOccupancyConfluence.text =
                    getString(R.string.occupancy_percentage_text, it.occupancy)
                textUpdatedAtConfluence.text = getString(
                    R.string.occupancy_timestamp_text,
                    dateFormatter.format(date),
                    timeFormatter.format(date)
                )
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun colorFromOccupancy(occupancy: Occupancy): Int {
        return when (occupancy.occupancy) {
            in 70..100 -> Color.RED
            in 50..70 -> Color.YELLOW
            in 30..50 -> Color.GREEN
            else -> Color.WHITE
        }
    }
}
