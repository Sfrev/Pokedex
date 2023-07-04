package ufscar.dc.Pokedex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val dataSet: List<List<String>>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label: TextView
        val value: TextView
        init {
            // Define click listener for the ViewHolder's View.
            label = view.findViewById(R.id.label_stats)
            value = view.findViewById(R.id.value_stats)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: CustomAdapter.ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOI" + dataSet.size + "\n" + dataSet + "\n" + dataSet.get(position))
        println(dataSet.get(position).get(0) + " " + dataSet.get(position).get(1))
        viewHolder.label.text = dataSet.get(position).get(0)
        viewHolder.value.text = dataSet.get(position).get(1)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
