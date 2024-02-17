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
import com.synrgyacademy.finalproject.utils.DateUtils.DDMMYYYYStriptoEEEddMMM
import com.synrgyacademy.finalproject.utils.CurrencyUtils.toIdrFormat

class DateTicketAdapter(
    var onclick: ((MinimumDataModel) -> Unit)? = null
) : ListAdapter<MinimumDataModel, RecyclerView.ViewHolder>(DIFF_UTIL) {

    var selectedItem: Int = -1

    inner class DateTicketViewHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MinimumDataModel) {
            binding.apply {

                if (bindingAdapterPosition == 0 && selectedItem == -1) {
                    dateTicket.setTextColor(binding.root.context.getColor(R.color.primary_blue))
                    airlineSmallestPrice.setTextColor(binding.root.context.getColor(R.color.primary_blue))
                    bottomLine.visibility = View.VISIBLE

                } else if (selectedItem == bindingAdapterPosition) {
                    dateTicket.setTextColor(binding.root.context.getColor(R.color.primary_blue))
                    airlineSmallestPrice.setTextColor(binding.root.context.getColor(R.color.primary_blue))
                    bottomLine.visibility = View.VISIBLE

                } else {
                    dateTicket.setTextColor(binding.root.context.getColor(R.color.neutral_06))
                    airlineSmallestPrice.setTextColor(binding.root.context.getColor(R.color.neutral_06))
                    bottomLine.visibility = View.GONE
                }

                dateTicket.text = data.date.DDMMYYYYStriptoEEEddMMM()
                airlineSmallestPrice.text = data.price.toIdrFormat()
            }
        }

        init {
            binding.root.setOnClickListener {
                selectedItem = bindingAdapterPosition
                onclick?.invoke(getItem(bindingAdapterPosition))
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return DateTicketViewHolder(
            ItemDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        (holder as DateTicketViewHolder).bind(data)
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