package com.synrgyacademy.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synrgyacademy.domain.model.airport.ScheduleDataModel
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.ItemTicketBinding
import com.synrgyacademy.finalproject.utils.convertDepartureArrivalTIme
import com.synrgyacademy.finalproject.utils.convertFlightTime
import com.synrgyacademy.finalproject.utils.numberToPrice

class TicketAdapter(
    var onclick: ((ScheduleDataModel) -> Unit)? = null
) : ListAdapter<ScheduleDataModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class AirportViewHolder(private val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ScheduleDataModel) {
            binding.apply {
                airlineName.text = data.companyName
                Glide.with(itemView.context)
                    .load(data.urlLogo)
                    .into(airlineLogo)

                flightPrice.text = numberToPrice(data.totalPrice)

                if (data.capacity > 20) {
                    flightSeat.apply {
                        text = binding.root.context.getString(R.string.text_available)
                        setTextColor(binding.root.context.getColor(R.color.secondary_success))
                    }
                } else {
                    flightSeat.apply {
                        text = binding.root.context.getString(R.string.text_tersisa, data.capacity)
                        setTextColor(binding.root.context.getColor(R.color.secondary_danger))
                    }
                }

                flightFrom.text = data.departureCode
                flightTo.text = data.arrivalCode
                flightStart.text = data.departureTime.convertDepartureArrivalTIme()
                flightEnd.text = data.arrivalTime.convertDepartureArrivalTIme()
                flightDuration.text = data.flightTime.convertFlightTime()
            }
        }

        init {
            binding.root.setOnClickListener {
                onclick?.invoke(getItem(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return AirportViewHolder(
            ItemTicketBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as AirportViewHolder).bind(data)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ScheduleDataModel>() {
            override fun areItemsTheSame(
                oldItem: ScheduleDataModel,
                newItem: ScheduleDataModel
            ): Boolean =
                oldItem.airplaneFlightTimeId == newItem.airplaneFlightTimeId

            override fun areContentsTheSame(
                oldItem: ScheduleDataModel,
                newItem: ScheduleDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()
        }
    }
}