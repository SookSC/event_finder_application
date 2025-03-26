package com.example.eventFinder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventFinder.model.EventResponse

class EventAdapter(private val context: Context, private val eventDataSet: ArrayList<EventResponse>):
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventName: TextView = view.findViewById(R.id.eventName)
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
        viewHolder.eventName.text = eventDataSet[position].title
        viewHolder.eventLocation.text = eventDataSet[position].location.toString() // TODO: Convert to city name
        viewHolder.eventCategory.text = eventDataSet[position].category
        viewHolder.eventStart.text = eventDataSet[position].start
        viewHolder.eventEnd.text = eventDataSet[position].end
    }

    override fun getItemCount() = eventDataSet.size
}