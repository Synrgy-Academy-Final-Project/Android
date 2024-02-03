package com.synrgyacademy.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synrgyacademy.domain.model.airport.AirportDataModel
import com.synrgyacademy.finalproject.databinding.ItemAirportBinding

class AirportAdapter(
    var onclick: ((AirportDataModel) -> Unit)? = null
) : ListAdapter<AirportDataModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class AirportViewHolder(private val binding: ItemAirportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AirportDataModel) {
            binding.apply {
                tvCity.text = data.airportCityCode
                tvAirport.text = data.airportCodeName
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
            ItemAirportBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<AirportDataModel>() {
            override fun areItemsTheSame(
                oldItem: AirportDataModel,
                newItem: AirportDataModel
            ): Boolean =
                oldItem.airportCode == newItem.airportCode

            override fun areContentsTheSame(
                oldItem: AirportDataModel,
                newItem: AirportDataModel
            ): Boolean =
                oldItem == newItem
        }
    }
}