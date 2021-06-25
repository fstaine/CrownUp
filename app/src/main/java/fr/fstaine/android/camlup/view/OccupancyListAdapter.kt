package fr.fstaine.android.camlup.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.fstaine.android.camlup.R
import fr.fstaine.android.camlup.persistence.entities.Occupancy

class OccupancyListAdapter : ListAdapter<Occupancy, OccupancyListAdapter.OccupancyViewHolder>(OccupancyComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OccupancyViewHolder {
        return OccupancyViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: OccupancyViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.toString())
    }

    class OccupancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val occupancyItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            occupancyItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): OccupancyViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return OccupancyViewHolder(view)
            }
        }
    }

    class OccupancyComparator : DiffUtil.ItemCallback<Occupancy>() {
        override fun areItemsTheSame(oldItem: Occupancy, newItem: Occupancy): Boolean {
            return oldItem.uid == newItem.uid && oldItem.occupancy == newItem.occupancy && oldItem.timestamp == newItem.timestamp
        }

        override fun areContentsTheSame(oldItem: Occupancy, newItem: Occupancy): Boolean {
            return oldItem.occupancy == newItem.occupancy && oldItem.timestamp == newItem.timestamp
        }
    }
}
