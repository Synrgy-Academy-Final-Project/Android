package com.synrgyacademy.finalproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.synrgyacademy.domain.model.airport.MinimumDataModel
import com.synrgyacademy.finalproject.R
import com.synrgyacademy.finalproject.databinding.ItemDateBinding
import com.synrgyacademy.finalproject.utils.toIdrFormat
import com.synrgyacademy.finalproject.utils.toIndonesiaDateVersion

class DateTicketAdapter(
    var onclick: ((MinimumDataModel) -> Unit)? = null
) : ListAdapter<MinimumDataModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private var selectedItem: Int = -1

    inner class AirportViewHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MinimumDataModel) {
            binding.apply {

                if (adapterPosition == 0 && selectedItem == -1) {
                    dateTicket.setTextColor(binding.root.context.getColor(R.color.primary_blue))
                    airlineSmallestPrice.setTextColor(binding.root.context.getColor(R.color.primary_blue))
                    bottomLine.visibility = View.VISIBLE

                } else if (selectedItem == adapterPosition) {
                    dateTicket.setTextColor(binding.root.context.getColor(R.color.primary_blue))
                    airlineSmallestPrice.setTextColor(binding.root.context.getColor(R.color.primary_blue))
                    bottomLine.visibility = View.VISIBLE

                } else {
                    dateTicket.setTextColor(binding.root.context.getColor(R.color.neutral_06))
                    airlineSmallestPrice.setTextColor(binding.root.context.getColor(R.color.neutral_06))
                    bottomLine.visibility = View.GONE
                }

                dateTicket.text = data.date.toIndonesiaDateVersion()
                airlineSmallestPrice.text = data.price.toIdrFormat()
            }
        }

        init {
            binding.root.setOnClickListener {
                val oldSelected = selectedItem
                selectedItem = adapterPosition
                onclick?.invoke(getItem(adapterPosition))
                notifyItemChanged(oldSelected) // Refresh the old selected item
                notifyItemChanged(selectedItem) // Refresh the new selected item
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return AirportViewHolder(
            ItemDateBinding.inflate(
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
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<MinimumDataModel>() {
            override fun areItemsTheSame(
                oldItem: MinimumDataModel,
                newItem: MinimumDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(
                oldItem: MinimumDataModel,
                newItem: MinimumDataModel
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()
        }
    }
}