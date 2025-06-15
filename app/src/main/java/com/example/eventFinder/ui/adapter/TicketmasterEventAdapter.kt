package com.example.eventFinder.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventFinder.R
import com.example.eventFinder.data.model.TicketmasterEventItem

class TicketmasterEventAdapter(private var eventDataSet: List<TicketmasterEventItem>): RecyclerView.Adapter<TicketmasterEventAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
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
        val location = event.embedded?.venues?.first() ?: event.place
        viewHolder.eventTitle.text = event.name
        viewHolder.eventLocation.text = location?.city?.name
        viewHolder.eventCategory.text = event.classifications?.first()?.segment?.name
        viewHolder.eventStart.text = event.dates.start.dateTime
    }

    override fun getItemCount() = eventDataSet.size

    fun updateEvents(newEvents: List<TicketmasterEventItem>) {
        eventDataSet = newEvents
        notifyDataSetChanged()
    }
}
