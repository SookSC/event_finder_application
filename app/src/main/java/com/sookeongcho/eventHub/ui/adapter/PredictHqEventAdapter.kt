package com.sookeongcho.eventHub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sookeongcho.eventHub.R
import com.sookeongcho.eventHub.data.model.PredictHqEventItem
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy '•' h:mma", Locale.ENGLISH)
        val parsedTime = OffsetDateTime.parse(event.start, DateTimeFormatter.ISO_DATE_TIME)

        viewHolder.eventTitle.text = event.title
        viewHolder.eventLocation.text = event.location.toString() // TODO: Convert to city name
        viewHolder.eventCategory.text = event.category
        viewHolder.eventStart.text = parsedTime.format(formatter)
    }

    override fun getItemCount() = eventDataSet.size

    fun updateEvents(newEvents: List<PredictHqEventItem>) {
        eventDataSet = newEvents
        notifyDataSetChanged()
    }
}
