package com.example.eventFinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventFinder.model.PredictHqEventItem

class PredictHqEventAdapter(private var eventDataSet: List<PredictHqEventItem>):
    RecyclerView.Adapter<PredictHqEventAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventTitle: TextView = view.findViewById(R.id.eventTitle)
        val eventLocation: TextView = view.findViewById(R.id.eventLocation)
        val eventCategory: TextView = view.findViewById(R.id.eventCategory)
        val eventStart: TextView = view.findViewById(R.id.eventStart)
        val eventEnd: TextView = view.findViewById(R.id.eventEnd)
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
        viewHolder.eventEnd.text = event.end
    }

    override fun getItemCount() = eventDataSet.size

    fun updateEvents(newEvents: List<PredictHqEventItem>) {
        eventDataSet = newEvents
        notifyDataSetChanged()
    }
}
