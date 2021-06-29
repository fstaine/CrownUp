package fr.fstaine.android.camlup.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fr.fstaine.android.camlup.CrowdUpApplication
import fr.fstaine.android.camlup.OccupancyViewModel
import fr.fstaine.android.camlup.OccupancyViewModelFactory
import fr.fstaine.android.camlup.databinding.FragmentListBinding
import fr.fstaine.android.camlup.ui.OccupancyListAdapter

class ListFragment : Fragment() {

    private lateinit var occupancyViewModel: OccupancyViewModel
    private var _binding: FragmentListBinding? = null

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

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = OccupancyListAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        occupancyViewModel.allOccupancies.observe(viewLifecycleOwner, Observer { occupancies ->
            occupancies?.let { adapter.submitList(it) }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
