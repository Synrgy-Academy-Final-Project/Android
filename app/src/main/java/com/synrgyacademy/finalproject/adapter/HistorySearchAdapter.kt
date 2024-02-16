package com.synrgyacademy.finalproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synrgyacademy.domain.model.airport.HistoryDataModel
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.ItemSearchHistoryBinding
import com.synrgyacademy.finalproject.utils.DateUtils.DDMMYYYYStripToDDMMMYYYY

class HistorySearchAdapter(
    var onclick: ((HistoryDataModel) -> Unit)? = null
) : ListAdapter<HistoryDataModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class HistorySearchViewHolder(private val binding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HistoryDataModel) {
            binding.apply {
                tvDeparture.text = data.departureData.airportCityCode
                tvArrival.text = data.arrivalData.airportCityCode
                tvDate.text = data.departureDate.DDMMYYYYStripToDDMMMYYYY()
                val countPassenger =
                    data.passenger.infant + data.passenger.adult + data.passenger.child
                tvPassenger.text = itemView.context.getString(R.string.text_passenger_with_value, countPassenger)
                tvClass.text = data.airplaneClass
            }
        }

        init {
            binding.root.setOnClickListener {
                onclick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return HistorySearchViewHolder(
            ItemSearchHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as HistorySearchViewHolder).bind(data)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<HistoryDataModel>() {
            override fun areItemsTheSame(
                oldItem: HistoryDataModel,
                newItem: HistoryDataModel
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: HistoryDataModel,
                newItem: HistoryDataModel
            ): Boolean =
                oldItem == newItem
        }
    }
}