package com.sookeongcho.eventHub.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sookeongcho.eventHub.R
import com.sookeongcho.eventHub.data.model.TicketmasterEventItem
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class TicketmasterEventAdapter(private var eventDataSet: List<TicketmasterEventItem>): RecyclerView.Adapter<TicketmasterEventAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val eventTitle: TextView = view.findViewById(R.id.tvEventTitle)
        val eventLocation: TextView = view.findViewById(R.id.tvEventLocation)
        val eventCategory: TextView = view.findViewById(R.id.tvEventCategory)
        val eventStart: TextView = view.findViewById(R.id.tvEventDateTime)
        val eventImage: ImageView = view.findViewById(R.id.ivEventImage)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_event_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val event = eventDataSet[position]
        val location = event.embedded?.venues?.first() ?: event.place
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy '•' h:mma", Locale.ENGLISH)
        val parsedTime = OffsetDateTime.parse(event.dates.start.dateTime, DateTimeFormatter.ISO_DATE_TIME)

        viewHolder.eventTitle.text = event.name
        viewHolder.eventLocation.text = location?.city?.name
        viewHolder.eventCategory.text = event.classifications?.first()?.segment?.name
        viewHolder.eventStart.text = parsedTime.format(formatter)

        Glide.with(viewHolder.itemView.context)
            .load(event.images?.first()?.url)
            .placeholder(R.drawable.ic_eh_logo)
            .error(R.drawable.ic_error_exclamation)
            .into(viewHolder.eventImage)
    }

    override fun getItemCount() = eventDataSet.size

    fun updateEvents(newEvents: List<TicketmasterEventItem>) {
        eventDataSet = newEvents
        notifyDataSetChanged()
    }
}
