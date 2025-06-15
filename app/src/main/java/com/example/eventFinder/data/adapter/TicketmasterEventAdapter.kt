package com.example.eventFinder.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventFinder.R
import com.example.eventFinder.data.model.TicketmasterEventItem

class TicketmasterEventAdapter(private var eventDataSet: List<TicketmasterEventItem>): RecyclerView.Adapter<TicketmasterEventAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
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
        val location = event.embedded?.venues?.first() ?: event.place
        viewHolder.eventTitle.text = event.name
        viewHolder.eventLocation.text = location?.city?.name
        viewHolder.eventCategory.text = event.classifications?.first()?.segment?.name
        viewHolder.eventStart.text = event.dates.start.dateTime
        viewHolder.eventEnd.text = event.dates.end?.dateTime
    }

    override fun getItemCount() = eventDataSet.size

    fun updateEvents(newEvents: List<TicketmasterEventItem>) {
        eventDataSet = newEvents
        notifyDataSetChanged()
    }
}
