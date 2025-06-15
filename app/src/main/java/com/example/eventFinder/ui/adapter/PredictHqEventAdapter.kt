package com.example.eventFinder.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventFinder.R
import com.example.eventFinder.data.model.PredictHqEventItem

class PredictHqEventAdapter(private var eventDataSet: List<PredictHqEventItem>):
    RecyclerView.Adapter<PredictHqEventAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventTitle: TextView = view.findViewById(R.id.tvEventTitle)
        val eventLocation: TextView = view.findViewById(R.id.tvEventLocation)
        val eventCategory: TextView = view.findViewById(R.id.tvEventCategory)
        val eventStart: TextView = view.findViewById(R.id.tvEventDateTime)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_event_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val event = eventDataSet[position]
        viewHolder.eventTitle.text = event.title
        viewHolder.eventLocation.text = event.location.toString() // TODO: Convert to city name
        viewHolder.eventCategory.text = event.category
        viewHolder.eventStart.text = event.start
    }

    override fun getItemCount() = eventDataSet.size

    fun updateEvents(newEvents: List<PredictHqEventItem>) {
        eventDataSet = newEvents
        notifyDataSetChanged()
    }
}
